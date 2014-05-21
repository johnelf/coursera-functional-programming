//List(1,2,3,4,5).fold(0)((sum, each) => each)
val list = List(1,2,3,4,5)

list.tail.foldLeft(list.head)((s,e) => s + e)

list.reduceLeft((s, e) => s + e)