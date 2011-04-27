
package physics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import actor.Positionable;
import math.Vector3;

public class SpatialTree<E extends Object & Positionable> implements Iterable<E> {
    private static final int DEFAULT_MAX_OBJECTS_PER_LEAF = 3;
    

    /**
     * Build a spatial tree from a collection of objects
     * @param objects
     */
    public SpatialTree(Collection <E> objects) {
        this(objects, DEFAULT_MAX_OBJECTS_PER_LEAF);
    }

    /**
     * Build a spatial tree from a collection of objects indicated the max leaf node size
     * @param objects
     * @param max_objects_per_leaf
     */
    public SpatialTree(Collection<E> objects, int max_objects_per_leaf) {
        this.max_objects_per_leaf = max_objects_per_leaf;
        find_bounds(objects);
        this.objects = new java.util.ArrayList<E>();
        for (E obj: objects)
            add(obj);
    }

    /**
     * Use to create child notes as we build the tree
     * @param min_x
     * @param min_y
     * @param min_z
     * @param max_x
     * @param max_y
     * @param max_z
     * @param max_objects_per_leaf
     */
    private SpatialTree(float min_x, float min_y, float min_z, float max_x, float max_y, float max_z, int max_objects_per_leaf) {
        this.max_x = max_x;
        this.max_y = max_y;
        this.max_z = max_z;
        this.min_x = min_x;
        this.min_y = min_y;
        this.min_z = min_z;
        this.max_objects_per_leaf = max_objects_per_leaf;
        this.objects = new java.util.ArrayList<E>();
        
        compute_divisions();
    }

    private void compute_divisions() {
        division_x = 0.5f * (max_x + min_x);
        division_y = 0.5f * (max_y + min_y);
        division_z = 0.5f * (max_z + min_z);
    }

    public boolean add(E e) {
        if (isLeaf())
            return objects.add(e);
        else
            return octant(e.getPosition()).add(e);
    }

    /**
     * Iterate through the objects in this leaf node
     */
    public Iterator<E> iterator() {
        return objects.iterator();
    }

    /**
     * Iterate through the leaf nodes in this tree
     * @return
     */
    public Iterator<SpatialTree<E>> leafInterator() {
        return new SpatialTreeIterator(this);
    }
    
    
    
    
    private SpatialTree<E> octant1; // +,+,+
    private SpatialTree<E> octant2; // -,+,+
    private SpatialTree<E> octant3; // -,-,+
    private SpatialTree<E> octant4; // +,-,+
    private SpatialTree<E> octant5; // +,+,-
    private SpatialTree<E> octant6; // -,+,-
    private SpatialTree<E> octant7; // -,-,-
    private SpatialTree<E> octant8; // +,-,-
    private List<E> objects;
    private float division_x;
    private float division_y;
    private float division_z;
    private float min_x;
    private float max_x;
    private float min_y;
    private float max_y;
    private float min_z;
    private float max_z;
    private final int max_objects_per_leaf;
    private boolean internal_node = false;
    

    private SpatialTree<E> octant(Vector3 position) {
        if (position.x > division_x) {
            if (position.y > division_y) {
                if (position.z > division_z)
                    return octant1;
                else
                    return octant5;
            } else {
                if (position.z > division_z)
                    return octant4;
                else
                    return octant8;
            }
        } else {
            if (position.y > division_y) {
                if (position.z > division_z)
                    return octant2;
                else
                    return octant6;
            } else {
                if (position.z > division_z)
                    return octant3;
                else
                    return octant7;
            }            
        }
    }

    private boolean isLeaf() {
        if (internal_node)
            return false;
        if (objects.size() < max_objects_per_leaf)
            return true;

        grow_leaves();
        return false;
    }

    /**
     * Turns a leaf node into an internal node
     */
    private void grow_leaves() {
        if (internal_node)
            return;

        octant1 = new SpatialTree<E>(division_x, division_y, division_z, max_x, max_y, max_z, max_objects_per_leaf);
        octant2 = new SpatialTree<E>(min_x, division_y, division_z, division_x, max_y, max_z, max_objects_per_leaf);
        octant3 = new SpatialTree<E>(min_x, min_y, division_z, division_x, division_y, max_z, max_objects_per_leaf);
        octant4 = new SpatialTree<E>(division_x, min_y, division_z, max_x, division_y, max_z, max_objects_per_leaf);
        octant5 = new SpatialTree<E>(division_x, division_y, min_z, max_x, max_y, division_z, max_objects_per_leaf);
        octant6 = new SpatialTree<E>(min_x, division_y, min_z, division_x, max_y, division_z, max_objects_per_leaf);
        octant7 = new SpatialTree<E>(min_x, min_y, min_z, division_x, division_y, division_z, max_objects_per_leaf);
        octant8 = new SpatialTree<E>(division_x, min_y, min_z, max_x, division_y, division_z, max_objects_per_leaf);
        internal_node = true;

        // Push all our objects into our children
        for (E obj: objects)
            octant(obj.getPosition()).add(obj);

        objects = null;
    }

    private void find_bounds(Collection<E> objects) {   
        for (E obj: objects) {
            Vector3 pos = obj.getPosition();
            max_x = Math.max(max_x, pos.x);
            min_x = Math.min(min_x, pos.x);
            max_y = Math.max(max_y, pos.y);
            min_y = Math.min(min_y, pos.y);
            max_z = Math.max(max_z, pos.z);
            min_z = Math.min(min_z, pos.z);
        }
        compute_divisions();
    }



    /**
     * An iterator for our spatial tree that iterates through our leaf nodes.
     * @author Dustin Lundquist
     */
    private class SpatialTreeIterator implements Iterator<SpatialTree<E>>  {
        private class History {
            SpatialTree<E> subTree;
            int lastOctent;

            History(SpatialTree<E> subTree) {
                this.subTree = subTree;
                this.lastOctent = 0;
            }
        }
        private Stack<History> history;

        public SpatialTreeIterator(SpatialTree<E> tree) {
            history = new Stack<History>();
            history.push(new History(tree));
        }

        @Override
        public boolean hasNext() {
            return history.size() > 1 || history.firstElement().lastOctent < 8;
        }

        @Override
        public SpatialTree<E> next() {
            History last = history.peek();
            if (last.subTree.internal_node) {
                SpatialTree<E> next;
                switch (last.lastOctent) {
                    case 0:
                        next = last.subTree.octant1;
                        break;
                    case 1:
                        next = last.subTree.octant2;
                        break;
                    case 2:
                        next = last.subTree.octant3;
                        break;
                    case 3:
                        next = last.subTree.octant4;
                        break;
                    case 4:
                        next = last.subTree.octant5;
                        break;
                    case 5:
                        next = last.subTree.octant6;
                        break;
                    case 6:
                        next = last.subTree.octant7;
                        break;
                    case 7:
                        next = last.subTree.octant8;
                        break;
                    default: // We have passed through all the descendant of this node
                        history.pop();
                        return next();
                }
                last.lastOctent++;
                history.push(new History(next));
                return next();
            }

            return history.pop().subTree;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
