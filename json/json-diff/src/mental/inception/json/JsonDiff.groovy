package mental.inception.json

import mental.inception.node.DiffNode

import com.mental.inception.json.JsonParser


class JsonDiff {

	private def formatFactory

	JsonDiff(formatFactory) {
		this.formatFactory = formatFactory
	}

	def compareDiff(value1, value2) {
		def diffKeys = buildDiffKeys(value1, value2)
		def tree = buildNodeTree(diffKeys)
		return createRootNode(tree)
	}

	private def buildDiffKeys(value1, value2) {
		JsonParser parser = new JsonParser()
		def json1 = parser.parse(value1)
		def json2 = parser.parse(value2)
		return new JsonDiffKeys(json1, json2)
	}

	private def buildNodeTree(diffKeys) {
		DiffNodeBuilder nodeBuilder = new DiffNodeBuilder(formatFactory, diffKeys)
		nodeBuilder.buildNodes()
		return nodeBuilder.getNodes()
	}

	private createRootNode(def value) {
		return new DiffNode(formatter:formatFactory.getFormatter("ROOT"), value:value)
	}
}
