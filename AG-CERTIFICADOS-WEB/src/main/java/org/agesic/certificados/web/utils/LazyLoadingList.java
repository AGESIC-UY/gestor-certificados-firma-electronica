package org.agesic.certificados.web.utils;

/*
 * Esta clase extiende una de AbsrtactList, y permite implementar una paginación
 * por base. A menos que el parámetro clearBufferedData sea false, la lista
 * siempre mantendrá en memoria, la página anterior, la actual, y una cantida de
 * páginas siguientes configurable.
 * Para el correcto funcionamiento, pagesBuffered debe ser siempre mayor o igual
 * a 2.
 */

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.sofis.gforbeon.persistence.dao.reference.EntityReference;
//import java.io.Serializable;
//
///**
// * 
// * @author Usuario
// */
//public class LazyLoadingList extends AbstractList implements Serializable {
//
//	private IDataProvider dataAdapter;
//	private int totalResultsNumber;
//	private int pageSize;
//	private int bufferSize;
//	private boolean clearBufferedData;
//	private int pagesBuffered;
//	/** cache of loadedData items */
//	private Map<Integer, EntityReference<Integer>> loadedData;
//
//	/**
//	 * Constructor de la lista
//	 * 
//	 * @param dataAdapter
//	 *            , the object that will perform the query
//	 * @param pageSize
//	 *            , the number of rows to be considered as "a page"
//	 * @param pagesBuffered
//	 *            , the number of pages to be buffered, must be 2 or greatter
//	 * @param clearBufferedData
//	 *            true if buffered data should be cleaned when it reach the
//	 *            number of pages to be buffered
//	 */
//	public LazyLoadingList(IDataProvider dataAdapter, int pageSize,
//			int pagesBuffered, boolean clearBufferedData) {
//		this.dataAdapter = dataAdapter;
//		this.totalResultsNumber = dataAdapter.getCountData();
//		this.pageSize = pageSize;
//		this.bufferSize = pageSize * pagesBuffered;
//		loadedData = new HashMap<Integer, EntityReference<Integer>>();
//		this.clearBufferedData = clearBufferedData;
//		this.pagesBuffered = pagesBuffered;
//	}
//
//	@Override
//	public EntityReference<Integer> get(int i) {
//		if (!loadedData.containsKey(i)) {
//			int startRow = 0;
//			if (this.clearBufferedData) {
//				clearMap();
//				startRow = getStartRow(i);
//			} else {
//				startRow = i;
//			}
//
//			int offset = bufferSize;
//			if ((startRow + offset) > totalResultsNumber) {
//				offset = totalResultsNumber - startRow;
//			}
//                         List<EntityReference<Integer>> results = dataAdapter
//					.getBufferedData(startRow, offset);
//			for (int j = 0; j < results.size(); j++) {
//                            	loadedData.put((startRow + j),
//						(EntityReference<Integer>) results.get(j));
//                                 
//			}
//		}
//		return loadedData.get(i);
//
//	}
//
//	/**
//	 * clears the map except the first element that MUST be kept
//	 */
//	private void clearMap() {
//		EntityReference<Integer> firstElement = loadedData.get(0);
//		loadedData.clear();
//		loadedData.put(0, firstElement);
//	}
//
//	/**
//	 * Calculates the index of the previous page's first element
//	 * 
//	 * @param i
//	 *            , the current row index
//	 * @return the index of the previous page's first element
//	 */
//	private int getStartRow(int i) {
//		int currentPage = (i / pageSize) + 1;
//		int firstIndexOfCurrentPage = pageSize * (currentPage - 1);
//		int firstIndexOfPreviusPage = firstIndexOfCurrentPage
//				- (bufferSize / pagesBuffered);
//		if (firstIndexOfPreviusPage < 0) {
//			firstIndexOfPreviusPage = 0;
//		}
//		return firstIndexOfPreviusPage;
//	}
//
//	@Override
//	public int size() {
//		return this.totalResultsNumber;
//	}
//
//	@Override
//	public void clear() {
//		totalResultsNumber = 0;
//		loadedData.clear();
//	}
//}
