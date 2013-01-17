require 'rake'
require 'ant'

MANIFEST = FileList["Rakefile", "Manifest.txt", "README.rdoc", "License.txt", "lib/**/*", "spec/**/*"]
File.open("Manifest.txt", "w") {|f| MANIFEST.each {|n| f.puts n } }

task :default => [:build]

file "lib/kryptproviderjdk.jar" => :build

desc "Delete artifact files"
task :clean do
  rm_f FileList['lib/kryptproviderjdk.jar']
  ant :clean
end

task :coverage_jar do
  ant 'coverage-jar'
end

desc "Build a JAR file"
task :build do
  ant 'jar'
end

task :java_compile => :build
