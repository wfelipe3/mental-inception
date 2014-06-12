package mental.inception.format

import groovy.json.JsonBuilder

class RootNodeJson {

	def format(node) {
		return buildJson(node)
	}

	private buildJson(node) {
		JsonBuilder jsonBuilder = new JsonBuilder()
		return jsonBuilder { root renderNode(node) }
	}

	private def renderNode(node) {
		return node.value.render()
	}
}
