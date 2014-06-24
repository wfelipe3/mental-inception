package mental.inception.json.builder

import mental.inception.node.DiffNode


class DiffTreeBuilder {

	private def nodeBuilderFactory
	private def node

	DiffTreeBuilder(nodeFactory) {
		this.nodeBuilderFactory = nodeFactory
	}

	def build(diffValues) {
		if (areDifferentType(diffValues)) {
			buildNodeWithDifferentType(diffValues)
		} else if (diffValues.areEmpty()) {
			buildEmptyNode(diffValues)
		} else {
			buildNodeWithSameType(diffValues)
		}
		return buidRootNode()
	}

	private areDifferentType(diffValues) {
		return !diffValues.areSameType()
	}

	private buildNodeWithDifferentType(diffValues) {
		def diffValue = new Expando(oldValue:diffValues.json1.value, newValue:diffValues.json2.value, type:"MODIFIED")
		node = buildNode('VALUE', diffValue)
	}

	private buildEmptyNode(diffValues) {
		switch (diffValues.json1.type) {
			case 'OBJECT':
				node = buildNode('OBJECT', [:])
				break
			case 'ARRAY':
				node = buildNode('ARRAY', [])
				break
		}
	}

	private buildNodeWithSameType(diffValues) {
		def objectValues = [:]
		def addedValues = diffValues.json2.value - diffValues.json1.value
		addedValues.each { key, value ->
			def addedValue = new Expando(oldValue:null, newValue:value, type:"ADDED")
			def valueNode = buildNode('VALUE', addedValue)
			objectValues.put(key, valueNode)
		}
		node = buildNode('OBJECT', objectValues)

		def removedValues = diffValues.json1.value - diffValues.json2.value
		removedValues.each { key, value ->
			def addedValue = new Expando(oldValue:value, newValue:null, type:"REMOVED")
			def valueNode = buildNode('VALUE', addedValue)
			objectValues.put(key, valueNode)
		}

		def equalKeyValues = diffValues.json1.value - (diffValues.json1.value - diffValues.json2.value)
		equalKeyValues.each { key, value ->
			def sameKeyValue = new Expando(oldValue:value, newValue:diffValues.json2.value[key], type:"SAME")
			def valueNode = buildNode('VALUE', sameKeyValue)
			objectValues.put(key, valueNode)
		}
		node = buildNode('OBJECT', objectValues)
	}

	private buidRootNode() {
		return buildNode('OBJECT', [root:node])
	}

	private buildNode(Object type, value) {
		def nodeBuilder = nodeBuilderFactory.getNodeBuilder(type)
		return nodeBuilder.buildNode(value)
	}
}
