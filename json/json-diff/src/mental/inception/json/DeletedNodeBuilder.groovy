package mental.inception.json

import mental.inception.node.DiffNode

class DeletedNodeBuilder {

	private def formatFactory

	DeletedNodeBuilder(formatFactory) {
		this.formatFactory = formatFactory
	}

	def buildNode(value) {
		return new DiffNode(formatter:getFormatter(), value:createValue(value))
	}

	private def getFormatter() {
		return formatFactory.getFormatter("VALUE")
	}

	private Expando createValue(value) {
		return new Expando(oldValue:value.oldValue, newValue:value.newValue, type:"DELETED")
	}
}
