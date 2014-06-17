package mental.inception.json.builder

import java.util.Map;

import mental.inception.json.DiffNodeBuilder;
import mental.inception.json.JsonDiffKeys;
import mental.inception.json.node.NodeBuilder;
import mental.inception.node.DiffNode;

class KeepedNodesBuilder {

	private def formatFactory
	private def nodeValues
	private def diffKeys

	KeepedNodesBuilder(formatFactory, diffKeys) {
		this.formatFactory = formatFactory
		this.diffKeys = diffKeys
		nodeValues = [:]
	}

	def buildNodes() {
		createKeepedKeysNodes()
		return nodeValues
	}

	private createKeepedKeysNodes() {
		diffKeys.keysInBothJson.each { key ->
			if (areBothObjects(key)) {
				addNode(key, createNodeObject(key))
			} else {
				addNode(key, createKeepedValueNode(key))
			}
		}
	}

	private boolean areBothObjects(key) {
		return diffKeys.json1[key] instanceof Map && diffKeys.json2[key] instanceof Map
	}

	private createNodeObject(String key) {
		def innerDiffKeys = new JsonDiffKeys(diffKeys.json1[key], diffKeys.json2[key])
		DiffNodeBuilder builder = new DiffNodeBuilder(formatFactory, innerDiffKeys)
		builder.buildNodes()
		return builder.getNodes()
	}

	private DiffNode createKeepedValueNode(String key) {
		def oldValue = diffKeys.json1[key]
		def newValue = diffKeys.json2[key]
		def formatter = formatFactory.getFormatter("VALUE")
		def type = getType(oldValue, newValue)
		def values = new Expando(oldValue:oldValue, newValue:newValue, type:type)
		NodeBuilder nodeBuilder = new NodeBuilder(formatter)
		return nodeBuilder.buildNode(values)
	}

	private String getType(oldValue, newValue) {
		if (oldValue == newValue) {
			return "SAME"
		} else {
			return "MODIFIED"
		}
	}

	private void addNode(key, node) {
		nodeValues.put key, node
	}
}
