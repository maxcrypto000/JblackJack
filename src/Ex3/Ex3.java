package Ex3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Ex3<T> {
	public Ex3() {}
	public Collection<T> invertiFiltraLista(List<T> daFiltrare, List<T> filtro) {
		if (filtro.isEmpty()) return daFiltrare.reversed();
		List<T> newList = daFiltrare.stream().filter(t -> !(t.equals(filtro.getFirst()))).toList();
		filtro.removeFirst();
		System.out.println(filtro);
		System.out.println(newList);
		return invertiFiltraLista(newList, filtro);
	}
	public static void main(String[] args) {
		Ex3<Integer> ex = new Ex3<Integer>();
		ArrayList<Integer> a1 = new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5, 7, 2, 5, 6, 0));
		ArrayList<Integer> a2 = new ArrayList<Integer>(Arrays.asList(5, 0, 4, 2));
		System.out.println(ex.invertiFiltraLista(a1,a2));
	}
}
