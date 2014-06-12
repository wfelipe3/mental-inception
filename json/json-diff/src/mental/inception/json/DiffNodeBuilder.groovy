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
			nodes = createObjectNode(nodeValues)
		}
	}

	private def createEmptyNode() {
		EmptyNodeBuilder builder = new EmptyNodeBuilder(formatFactory)
		return builder.buildNode("")
	}

	private createKeepedKeysNodes(nodeValues) {
		diffKeys.keysInBothJson.each { key ->
			if (areBothObjects(key)) {
				createNodeObject(key, nodeValues)
			} else {
				nodeValues.put(key, createKeepedValueNode(key))
			}
		}
	}

	private createDeletedKeysNodes(nodeValues) {
		diffKeys.keysOnlyInJson1.each { key ->
			nodeValues.put(key, createDeletedValueNode(key))
		}
	}

	private createAddedKeysNodes(nodeValues) {
		diffKeys.keysOnlyInJson2.each { key ->
			nodeValues.put(key, createAddedValueNode(key))
		}
	}

	private boolean areBothObjects(key) {
		return diffKeys.json1[key] instanceof Map && diffKeys.json2[key] instanceof Map
	}

	private createNodeObject(String key, Map nodeValues) {
		def nodeValue = createObjectInnerNode(key)
		nodeValues.put(key, nodeValue)
	}

	private DiffNode createKeepedValueNode(String key) {
		def oldValue = diffKeys.json1[key]
		def newValue = diffKeys.json2[key]
		def values = new Expando(oldValue:oldValue, newValue:newValue)
		KeepedNodeBuilder nodeBuilder = new KeepedNodeBuilder(formatFactory)
		return nodeBuilder.buildNode(values)
	}

	private DiffNode createDeletedValueNode(String key) {
		def oldValue = diffKeys.json1[key]
		def newValue = diffKeys.json2[key]
		def values = new Expando(oldValue:oldValue, newValue:newValue)
		DeletedNodeBuilder nodeBuilder = new DeletedNodeBuilder(formatFactory)
		return nodeBuilder.buildNode(values)
	}

	private DiffNode createAddedValueNode(String key) {
		def oldValue = diffKeys.json1[key]
		def newValue = diffKeys.json2[key]
		def values = new Expando(oldValue:oldValue, newValue:newValue)
		AddedNodeBuilder nodeBuilder = new AddedNodeBuilder(formatFactory)
		return nodeBuilder.buildNode(values)
	}

	private createObjectInnerNode(String key) {
		def innerDiffKeys = new JsonDiffKeys(diffKeys.json1[key], diffKeys.json2[key])
		DiffNodeBuilder builder = new DiffNodeBuilder(formatFactory, innerDiffKeys)
		builder.buildNodes()
		return builder.getNodes()
	}

	private createObjectNode(nodeValues) {
		ObjectNodeBuilder nodeBuilder = new ObjectNodeBuilder(formatFactory)
		return nodeBuilder.buildNode(nodeValues)
	}

	def getNodes() {
		return nodes
	}
}
