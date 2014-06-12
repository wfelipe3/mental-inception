package mental.inception.format

import groovy.json.JsonBuilder

class EmptyNodeJson {

	def format(node) {
		JsonBuilder builder = new JsonBuilder()
		return builder{}
	}
}
