import sys
import os

folders = ["../src","../test"]

types = {}

def findlines(path):
    f = open(path)
    contents = f.read()
    f.close()
    return len(contents.split("\n"))

for path in folders:
    for (root, dirs, files) in os.walk(path):
        for name in files:
            name_split = name.split('.')
            ext =(name_split[len(name_split)-1])
            if 
            types[ext]={}
            if ext[0] in types[ext]:
                types[ext][0] += 1
            else:
                types[ext][0] = 1
            lines = findlines(root+"/"+name)
            
print("type\tfiles\tlines")
for type, stats in types.items():
    print(type+"\t"+str(stats[0])+"\t"+str(stats[0]))