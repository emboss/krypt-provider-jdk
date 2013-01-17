Gem::Specification.new do |s|
  s.name = 'krypt-provider-jdk'
  s.version = '0.0.1'
  s.author = 'Hiroshi Nakamura, Martin Bosslet'
  s.email = 'Martin.Bosslet@googlemail.com'
  s.homepage = 'https://github.com/krypt/krypt-provider-jdk'
  s.summary = 'Java implementation of the krypt-provider API using the standard JDK security library'
  s.files = ["Rakefile", "License.txt", "README.rdoc", "Manifest.txt", "lib/kryptproviderjdk.jar"] + Dir.glob('{bin,lib,spec,test}/**/*')
  s.test_files = Dir.glob('test/**/test_*.rb')
  s.require_path = "lib"
end
