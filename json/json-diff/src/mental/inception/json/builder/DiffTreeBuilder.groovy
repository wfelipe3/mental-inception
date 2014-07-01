package mental.inception.json.builder



class DiffTreeBuilder {

	private def nodeBuilderFactory
	private def node

	DiffTreeBuilder(nodeFactory) {
		this.nodeBuilderFactory = nodeFactory
	}

	def build(diffValues) {
		if (areDifferentType(diffValues))
			return buildDifferentTypeNodeWith(diffValues)
		else
			return buildNodeWith(diffValues)
	}

	private areDifferentType(diffValues) {
		return !diffValues.areSameType()
	}

	private def buildDifferentTypeNodeWith(diffValues) {
		def value1 = diffValues.json1.value
		def value2 = diffValues.json2.value
		def diffValue = new Expando(oldValue:value1, newValue:value2, type:"MODIFIED")
		return buildNode('VALUE', diffValue)
	}

	private def buildNodeWith(diffValues) {
		def nodeBuilder = getNodeBuilder(diffValues.getType())
		return nodeBuilder.buildNode(diffValues)
	}

	private getNodeBuilder(type) {
		switch (type) {
			case 'OBJECT':
				return new ObjectNodeBuilder(nodeBuilderFactory)
			case 'ARRAY':
				return new ArrayNodeBuilder(nodeBuilderFactory)
		}
	}

	private buildNode(Object type, value) {
		def nodeBuilder = nodeBuilderFactory.getNodeBuilder(type)
		return nodeBuilder.buildNode(value)
	}
}
