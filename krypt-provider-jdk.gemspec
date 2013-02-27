Gem::Specification.new do |s|

  s.name = 'krypt-provider-jdk'
  s.version = '0.0.1'

  s.author = 'Hiroshi Nakamura, Martin Bosslet'
  s.email = 'Martin.Bosslet@gmail.com'
  s.homepage = 'https://github.com/krypt/krypt-provider-jdk'
  s.summary = 'Default provider for JRuby'
  s.description = 'Java implementation of the krypt-provider API using the standard JDK security library'

  s.required_ruby_version     = '>= 1.9.3'

  s.files = ["Rakefile", "LICENSE", "README.rdoc", "Manifest.txt", "lib/kryptproviderjdk.jar"] + Dir.glob('{bin,lib,spec,test}/**/*')
  s.test_files = Dir.glob('test/**/test_*.rb')
  s.require_path = "lib"
  s.license = 'MIT'

end
