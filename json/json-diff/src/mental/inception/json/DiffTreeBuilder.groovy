package mental.inception.json

import mental.inception.node.DiffNode

class DiffTreeBuilder {

	private def formatFactory

	DiffTreeBuilder(formatFactory) {
		this.formatFactory = formatFactory
	}

	def build(diffKeys) {
		def value = buildNodeValue(diffKeys)
		return createRootNode(value)
	}

	private def buildNodeValue(diffKeys) {
		DiffNodeBuilder nodeBuilder = new DiffNodeBuilder(formatFactory, diffKeys)
		nodeBuilder.buildNodes()
		return nodeBuilder.getNodes()
	}

	private createRootNode(def value) {
		return new DiffNode(formatter:formatFactory.getFormatter("ROOT"), value:value)
	}
}
