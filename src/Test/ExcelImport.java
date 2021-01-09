package test;
import java.io.File;
import jxl.Sheet;
import jxl.Workbook;
public class ExcelImport {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		ExcelImport excelImport = new ExcelImport();
		excelImport.getAllByExcel("E:\\����ļ�\\Java\\�γ����\\Excel\\participant.xls");
	}
	public static void getAllByExcel(String file) {
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// ����rwb.getSheet(0)
			int clos = rs.getColumns();// �õ����е���
			int rows = rs.getRows();// �õ����е���
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// ��һ�����������ڶ���������
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