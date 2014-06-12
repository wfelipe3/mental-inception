package mental.inception.json

import mental.inception.node.DiffNode

class EmptyNodeBuilder {

	private def formatFactory
	
	EmptyNodeBuilder(formatFactory) {
		this.formatFactory = formatFactory
	}
	
	def buildNode(value) {
		def formatter = formatFactory.getFormatter("EMPTY")
		return new DiffNode(formatter:formatter, value:"")
	}
}
