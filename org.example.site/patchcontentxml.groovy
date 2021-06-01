// Add copies of all referenced <repository> elements with type=1
// See https://www.eclipse.org/lists/epsilon-dev/msg00522.html for context
// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=565859
// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=453708
// See https://github.com/eclipse/tycho/issues/141

File contentXml = new File("target/repository/content.xml")
def patchedContentXml = new File("target/repository/content.xml")

def document = new XmlParser().parse(contentXml)
def references = document.references[0]

for (repository in references.repository) {
	def type1Repository = references.appendNode("repository");
	type1Repository.@uri = repository.@uri
	type1Repository.@url = repository.@url
	type1Repository.@options = "1"
	type1Repository.@type = "1"
}

references.@size = (references.@size as Integer) * 2

new XmlNodePrinter(new PrintWriter(new FileWriter(patchedContentXml))).print(document)

