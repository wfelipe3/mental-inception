package mental.inception.json

import mental.inception.node.DiffNode

class SameNodeBuilder {

	private def formatFactory
	
	SameNodeBuilder(formatFactory) {
		this.formatFactory = formatFactory
	}
	
	def buildNode(value) {
		def formatter = formatFactory.getFormatter("VALUE")
		def changedValues = new Expando(oldValue:value.oldValue, newValue:value.newValue, type:"SAME")
		return new DiffNode(formatter:formatter, value:changedValues)
	}
}
