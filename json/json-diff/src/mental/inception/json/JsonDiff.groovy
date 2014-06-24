package mental.inception.json

import mental.inception.json.builder.DiffTreeBuilder;
import mental.inception.json.node.DiffNodeBuilder;
import mental.inception.node.DiffNode

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
		JsonDiffParser parser = new JsonDiffParser(value1:value1, value2:value2)
		return parser.parseKeys()
	}

	private def buildNodeTree(diffKeys) {
		DiffTreeBuilder treeBuilder = new DiffTreeBuilder(nodeBuilderFactory)
		return treeBuilder.build(diffKeys)
	}
}
