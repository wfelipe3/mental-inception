package mental.inception.json.node

import mental.inception.node.DiffNode

class AddedNodeBuilder {

	private def formatFactory

	AddedNodeBuilder(formatFactory) {
		this.formatFactory = formatFactory
	}

	def buildNode(value) {
		return new DiffNode(formatter:getFormatter(), value:createValue(value))
	}

	private def getFormatter() {
		return formatFactory.getFormatter("VALUE")
	}

	private Expando createValue(value) {
		return new Expando(oldValue:value.oldValue, newValue:value.newValue, type:"ADDED")
	}
}
