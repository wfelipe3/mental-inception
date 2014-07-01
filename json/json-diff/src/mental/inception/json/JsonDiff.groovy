package mental.inception.json

import mental.inception.json.builder.DiffRootNodeBuilder

import com.mental.inception.json.JsonParser


class JsonDiff {

	private def nodeBuilderFactory

	JsonDiff(nodeBuilderFactory) {
		this.nodeBuilderFactory = nodeBuilderFactory
	}

	def compareDiff(value1, value2) {
		def diffKeys = buildDiffKeys(value1, value2)
		return buildNodeTree(diffKeys)
	}

	private def buildDiffKeys(value1, value2) {
		return createDiffParser(value1, value2).parseKeys()
	}

	private JsonDiffParser createDiffParser(value1, value2) {
		JsonParser jsonParser = new JsonParser()
		return new JsonDiffParser(jsonParser, value1, value2)
	}

	private def buildNodeTree(diffKeys) {
		DiffRootNodeBuilder treeBuilder = new DiffRootNodeBuilder(nodeBuilderFactory)
		return treeBuilder.build(diffKeys)
	}
}
