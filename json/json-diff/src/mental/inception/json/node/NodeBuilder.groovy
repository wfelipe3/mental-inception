package mental.inception.json.node

import mental.inception.node.DiffNode

class NodeBuilder {

	private def formatter

	NodeBuilder(formatter) {
		this.formatter = formatter
	}

	def buildNode(value) {
		return new DiffNode(formatter:formatter, value:createValue(value))
	}

	private Expando createValue(value) {
		return new Expando(oldValue:value.oldValue, newValue:value.newValue, type:value.type)
	}
}
