package mental.inception.json

import mental.inception.node.DiffNode

class DiffNodeBuilder {

	private def formatFactory
	private def diffKeys
	private def nodes

	DiffNodeBuilder(formatFactory, diffKeys) {
		this.formatFactory = formatFactory
		this.diffKeys = diffKeys
	}

	void buildNodes() {
		if (diffKeys.areBothKeysEmpty()) {
			nodes = createEmptyNode()
		} else {
			Map nodeValues = [:] as TreeMap
			createKeepedKeysNodes(nodeValues)
			createDeletedKeysNodes(nodeValues)
			createAddedKeysNodes(nodeValues)
		}
	}

	private def createEmptyNode() {
		def formatter = formatFactory.getFormatter("EMPTY")
		return new DiffNode(formatter:formatter, value:"")
	}

	private createKeepedKeysNodes(nodeValues) {
		diffKeys.keysInBothJson.each { key ->
			if (areBothObjects(key)) {
				createNodeObject(key, nodeValues)
			} else {
				createNodeValue(key, nodeValues)
			}
		}
		createObjectNode(nodeValues)
	}
	
	private createDeletedKeysNodes(nodeValues) {
		diffKeys.keysOnlyInJson1.each { key ->
			createNodeValue(key, nodeValues)
		}
		createObjectNode(nodeValues)
	}
	
	private createAddedKeysNodes(nodeValues) {
		diffKeys.keysOnlyInJson2.each { key ->
			createNodeValue(key, nodeValues)
		}
		createObjectNode(nodeValues)
	}

	private boolean areBothObjects(key) {
		return diffKeys.json1[key] instanceof Map && diffKeys.json2[key] instanceof Map
	}
	
	private createNodeObject(String key, Map nodeValues) {
		def nodeValue = createObjectInnerNode(key)
		nodeValues.put(key, nodeValue)
	}

	private createNodeValue(String key, Map nodeValues) {
		def nodeValue = createValueNode(key)
		nodeValues.put(key, nodeValue)
	}
	
	private DiffNode createValueNode(String key) {
		def oldValue = diffKeys.json1[key]
		def newValue = diffKeys.json2[key]
		def formatter = formatFactory.getFormatter("VALUE")
		def changedValues = new Expando(oldValue:oldValue, newValue:newValue)
		return new DiffNode(formatter:formatter, value:changedValues)
	}
	
	private createObjectInnerNode(String key) {
		def innerDiffKeys = new JsonDiffKeys(diffKeys.json1[key], diffKeys.json2[key])
		DiffNodeBuilder builder = new DiffNodeBuilder(formatFactory, innerDiffKeys)
		builder.buildNodes()
		return builder.getNodes()
	}

	private createObjectNode(def nodeValues) {
		def formatter = formatFactory.getFormatter("OBJECT")
		nodes = new DiffNode(formatter:formatter, value:nodeValues)
	}

	def getNodes() {
		return nodes
	}
}
