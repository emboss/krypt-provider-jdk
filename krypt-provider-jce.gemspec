Gem::Specification.new do |s|
  s.name = 'krypt-provider-jce'
  s.version = '0.0.1'
  s.author = 'Hiroshi Nakamura, Martin Bosslet'
  s.email = 'Martin.Bosslet@googlemail.com'
  s.homepage = 'https://github.com/krypt/krypt-provider-jce'
  s.summary = 'Java implementation of the krypt-provider API using the standard JDK Java Cryptography Extension'
  s.files = ["Rakefile", "License.txt", "README.rdoc", "Manifest.txt", "lib/kryptprovider.jar"] + Dir.glob('{bin,lib,spec,test}/**/*')
  s.test_files = Dir.glob('test/**/test_*.rb')
  s.require_path = "lib"
end
