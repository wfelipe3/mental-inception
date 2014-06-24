package mental.inception.json.builder

class NodeBuilderFactory {

	private def formatFactory

	NodeBuilderFactory(formatFactory) {
		this.formatFactory = formatFactory
	}

	def getNodeBuilder(type) {
		return new NodeBuilder(formatFactory, type)
	}
}
