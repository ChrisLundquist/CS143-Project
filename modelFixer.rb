#!/usr/bin/ruby

filePaths = ARGV

puts filePaths.inspect

#begin
filePaths.each do |filepath|
  file = File.new(filepath)

  points =  { :x => [], :y => [], :z => []}

  file.each do |line|
    next unless line.start_with?("v ")
    line = line.split
    points[:x].push( line[1].to_f )
    points[:y].push( line[2].to_f )
    points[:z].push( line[3].to_f )
  end

  [points[:x],points[:y],points[:z]].each do |axis|
    average = axis.reduce { |sum, value| sum + value }
    average /= axis.size
    axis.map! { |i| i - average }
  end

  file.rewind
  fixedFile = File.open(filepath + ".fixed", "w")

  vertexCount = 0;
  file.each do |line|
    # If it is a vertex, we need to replace it with the modified one
    if(line.start_with?("v "))
      fixedFile.puts("v " + points[:x][vertexCount].to_s + " " + points[:y][vertexCount].to_s + " " + points[:z][vertexCount].to_s)
      vertexCount += 1
    else
      # Write the line straight through without modifying it
      fixedFile.write(line)
    end
  end
  fixedFile.close
  file.close
end

