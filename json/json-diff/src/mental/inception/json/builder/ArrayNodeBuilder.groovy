package mental.inception.json.builder

class ArrayNodeBuilder {

	private def nodeBuilderFactory
	private def arrayValues = []

	ArrayNodeBuilder(nodeBuilderFactory) {
		this.nodeBuilderFactory = nodeBuilderFactory
	}

	def buildNode(diffValues) {
		if (diffValues.areEmpty())
			return buildEmptyNode()
		else
			return buildArrayNode(diffValues)
	}

	private def buildEmptyNode() {
		return buildDiffNode('ARRAY', [])
	}

	private buildArrayNode(diffValues) {
		if (diffValues.json1.value.size() <= diffValues.json2.value.size()) {
			diffValues.json1.value.eachWithIndex { value, i ->
				if (value == diffValues.json2.value[i]) {
					def arrayValue = new Expando(oldValue:value, newValue:value, type:"SAME")
					def valueNode = buildDiffNode("VALUE", arrayValue)
					arrayValues << valueNode
				} else {
					def arrayValue = new Expando(oldValue:value, newValue:diffValues.json2.value[i], type:"MODIFIED")
					def valueNode = buildDiffNode("VALUE", arrayValue)
					arrayValues << valueNode
				}
			}

			diffValues.json1.value.size().step(diffValues.json2.value.size(), 1) { i ->
				def arrayValue = new Expando(oldValue:null, newValue:diffValues.json2.value[i], type:"ADDED")
				def valueNode = buildDiffNode("VALUE", arrayValue)
				arrayValues << valueNode
			}
		} else {
			diffValues.json2.value.eachWithIndex { value, i ->
				if (value == diffValues.json1.value[i]) {
					def arrayValue = new Expando(oldValue:value, newValue:value, type:"SAME")
					def valueNode = buildDiffNode("VALUE", arrayValue)
					arrayValues << valueNode
				}
			}

			diffValues.json2.value.size().step(diffValues.json1.value.size(), 1) { i ->
				def arrayValue = new Expando(oldValue:diffValues.json1.value[i], newValue:null, type:"REMOVED")
				def valueNode = buildDiffNode("VALUE", arrayValue)
				arrayValues << valueNode
			}
		}
		return buildDiffNode('ARRAY', arrayValues)
	}

	private buildDiffNode(type, value) {
		def nodeBuilder = nodeBuilderFactory.getNodeBuilder(type)
		return nodeBuilder.buildNode(value)
	}
}
