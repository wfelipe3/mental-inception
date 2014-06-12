package mental.inception.node

import mental.inception.format.ObjectNodeJson

class DiffNode {

	def formatter
	def value

	def render() {
		return formatter.format(this)
	}
}
