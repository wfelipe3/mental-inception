package mental.inception.json.node

import mental.inception.node.DiffNode

class ObjectNodeBuilder {

	private def formatFactory

	ObjectNodeBuilder(formatFactory) {
		this.formatFactory = formatFactory
	}

	def buildNode(value) {
		return new DiffNode(formatter:getFormatter(), value:value)
	}

	private def getFormatter() {
		return formatFactory.getFormatter("OBJECT")
	}
}
