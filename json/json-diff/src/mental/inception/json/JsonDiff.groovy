package mental.inception.json

import com.mental.inception.json.JsonParser


class JsonDiff {

	private JsonDiffKeys diffKeys
	private def treeBuilder

	JsonDiff(nodeTreeBuilder, value1, value2) {
		this.treeBuilder = nodeTreeBuilder
		createJsonDiffKeys(value1, value2)
	}

	def compareDiff() {
		return treeBuilder.build(diffKeys)
	}

	private void createJsonDiffKeys(value1, value2) {
		JsonParser parser = new JsonParser()
		def json1 = parser.parse(value1)
		def json2 = parser.parse(value2)
		diffKeys = new JsonDiffKeys(json1, json2)
	}
}
