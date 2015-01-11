package ar.edu.unicen.exa.papilio.core.as;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public enum CollectionTypes {
		
		COLLECTION(Collection.class.getSimpleName()),
		MAP(Map.class.getSimpleName()),
		SORTED_MAP(SortedMap.class.getSimpleName()),
		LIST(List.class.getSimpleName()),
		SET(Set.class.getSimpleName()),
		SORTED_SET(SortedSet.class.getSimpleName()),
		QUEUE(Queue.class.getSimpleName());
		
		private String name;

		CollectionTypes(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
}
