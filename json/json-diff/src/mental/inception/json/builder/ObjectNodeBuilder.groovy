package mental.inception.json.builder

import mental.inception.json.JsonDiffParser

class ObjectNodeBuilder {

	private def nodeBuilderFactory
	private def objectValues = [:]

	ObjectNodeBuilder(nodeBuilderFactory) {
		this.nodeBuilderFactory = nodeBuilderFactory
	}

	def buildNode(diffValues) {
		if (diffValues.areEmpty())
			return buildEmptyNode()
		else
			return buildObjectNode(diffValues)
	}

	private def buildEmptyNode() {
		return buildDiffNode('OBJECT', [:])
	}

	private buildObjectNode(diffValues) {
		def addedValues = diffValues.json2.value.keySet() - diffValues.json1.value.keySet()
		addedValues.each { key ->
			def addedValue = new Expando(oldValue:null, newValue:diffValues.json2.value[key], type:"ADDED")
			def valueNode = buildDiffNode('VALUE', addedValue)
			objectValues.put(key, valueNode)
		}

		def removedValues = diffValues.json1.value.keySet() - diffValues.json2.value.keySet()
		removedValues.each { key ->
			def addedValue = new Expando(oldValue:diffValues.json1.value[key], newValue:null, type:"REMOVED")
			def valueNode = buildDiffNode('VALUE', addedValue)
			objectValues.put(key, valueNode)
		}

		def equalKeyValues = diffValues.json1.value.keySet() - (diffValues.json1.value.keySet() - diffValues.json2.value.keySet())
		equalKeyValues.each { key ->
			if (diffValues.json1.value[key] == diffValues.json2.value[key]) {
				if (diffValues.json1.value[key] instanceof Map) {
					JsonDiffParser parser = new JsonDiffParser(new ObjectParser(), diffValues.json1.value[key], diffValues.json2.value[key])
					def keys = parser.parseKeys()
					DiffTreeBuilder builder = new DiffTreeBuilder(nodeBuilderFactory)
					objectValues.put(key, builder.build(keys))
				} else {
					def sameKeyValue = new Expando(oldValue: diffValues.json1.value[key], newValue:diffValues.json2.value[key], type:"SAME")
					def valueNode = buildDiffNode('VALUE', sameKeyValue)
					objectValues.put(key, valueNode)
				}
			} else {
				def sameKeyValue = new Expando(oldValue: diffValues.json1.value[key], newValue:diffValues.json2.value[key], type:"MODIFIED")
				def valueNode = buildDiffNode('VALUE', sameKeyValue)
				objectValues.put(key, valueNode)
			}
		}

		return buildDiffNode('OBJECT', objectValues)
	}

	private buildDiffNode(Object type, value) {
		def nodeBuilder = nodeBuilderFactory.getNodeBuilder(type)
		return nodeBuilder.buildNode(value)
	}
}
