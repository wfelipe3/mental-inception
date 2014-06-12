package mental.inception.format

import groovy.json.JsonBuilder;

class ObjectNodeJson {

	def format(node) {
		JsonBuilder jsonBuilder = new JsonBuilder()
		return jsonBuilder {
			node.value.each { key, value ->
				"$key" getValue(value)
			}
		}
	}

	private getValue(value) {
		return value.render()
	}
}
