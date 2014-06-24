package mental.inception.json.builder

import mental.inception.node.DiffNode

class NodeBuilder {

	private def formatFactory
	private def type

	NodeBuilder(formatFactory, type) {
		this.formatFactory = formatFactory
		this.type = type
	}

	def buildNode(value) {
		def formatter = formatFactory.getFormatter(type)
		return new DiffNode(formatter:formatter, value:value)
	}
}
