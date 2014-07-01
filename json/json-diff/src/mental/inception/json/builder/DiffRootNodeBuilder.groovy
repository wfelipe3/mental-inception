package mental.inception.json.builder

class DiffRootNodeBuilder {

	private def nodeBuilderFactory
	private def node

	DiffRootNodeBuilder(nodeFactory) {
		this.nodeBuilderFactory = nodeFactory
	}

	def build(diffValues) {
		buildNodeTree(diffValues)
		return buidRootNode()
	}

	private void buildNodeTree(diffKeys) {
		DiffTreeBuilder treeBuilder = new DiffTreeBuilder(nodeBuilderFactory)
		node = treeBuilder.build(diffKeys)
	}

	private buidRootNode() {
		return buildNode('OBJECT', [root:node])
	}

	private buildNode(Object type, value) {
		def nodeBuilder = nodeBuilderFactory.getNodeBuilder(type)
		return nodeBuilder.buildNode(value)
	}
}
