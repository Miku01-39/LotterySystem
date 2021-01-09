package test;
import java.io.File;
import jxl.Sheet;
import jxl.Workbook;
public class ExcelImport {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		ExcelImport excelImport = new ExcelImport();
		excelImport.getAllByExcel("E:\\编程文件\\Java\\课程设计\\Excel\\participant.xls");
	}
	public static void getAllByExcel(String file) {
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// 第一个是列数，第二个是行数
					String name = rs.getCell(j, i).getContents();
					System.out.println(" name:" + name);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}