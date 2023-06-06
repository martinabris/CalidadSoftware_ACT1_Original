/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falabella.services;

import com.falabella.main.Program;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author ext_ealinares
 */
public class FileManagerService extends CopySheets {

    private String template_file_path;
    private String template_file_name;
    private String input_file_folder;
    private String output_file_folder;
    private Properties props;
    private String _file_path;
    private String _extension_file;

    public FileManagerService() {

        super();
        this.props = new Properties();
        String _prop_path = "src/resources/properties/properties.properties";

        try {
            props.load(new FileInputStream(_prop_path));
            this.setTemplate_file_path(
                    props.getProperty("template_path_file").trim());
            this.setTemplate_file_name(
                    props.getProperty("template_file_name").trim());
            this.setInput_file_folder(
                    props.getProperty("input_file_folder").trim());
            this.setOutput_file_folder(
                    props.getProperty("output_file_folder").trim());

        }
        catch (IOException ex) {
            System.out.println("Read Error in Properties File.\n" + _prop_path);
            Logger.getLogger(
                    FileManagerService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        catch (Exception ex) {
            System.out.println("Read Error in Properties File.\n" + _prop_path);
            Logger.getLogger(
                    FileManagerService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public String getTemplate_file_path() {

        return template_file_path;
    }

    public void setTemplate_file_path(String template_file_path) {
        this.template_file_path = template_file_path;
    }

    public String getInput_file_folder() {
        return input_file_folder;
    }

    public void setInput_file_folder(String input_file_folder) {
        this.input_file_folder = input_file_folder;
    }

    public String getOutput_file_folder() {
        return output_file_folder;
    }

    public void setOutput_file_folder(String output_file_folder) {
        this.output_file_folder = output_file_folder;
    }

    public String getTemplate_file_name() {
        return template_file_name;
    }

    public void setTemplate_file_name(String template_file_name) {
        this.template_file_name = template_file_name;
    }

    public String getFile_path() {
        return _file_path;
    }

    public void setFile_path(String _prop_name, String _input_file) {

        String _file_separator = null;

        String _os_name = System.getProperty("os.name").toUpperCase();

        if (_os_name.contains("WINDOWS")) {

            _file_separator = "/";
        }
        else {

            _file_separator = System.getProperty("file.separator");
        }

        this._file_path
                = _prop_name.concat(_file_separator).concat(_input_file);

    }

    public String getExtension_file() {
        return _extension_file;
    }

    public void setExtension_file(String _extension_file) {
        this._extension_file = _extension_file;
    }

    public void mergeExcelFiles(File myFile) {

        try {

            this.setFile_path(
                    this.getTemplate_file_path(),
                    this.getTemplate_file_name());

            File templateFile = new File(this.getFile_path());

            Workbook template_Workbook = WorkbookFactory.create(templateFile);

            InputStream file = new FileInputStream(myFile);

            List<InputStream> myList = new ArrayList<>();

            myList.add(file);

            Workbook output_Workbook
                    = mergeExcelFiles(template_Workbook, myList);

            setFile_path(this.getOutput_file_folder(), myFile.getName());

            FileOutputStream out
                    = new FileOutputStream(new File(this.getFile_path()));
            output_Workbook.write(out);

        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(
                    FileManagerService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(
                    FileManagerService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    public void CopyDataBetweenWorkbooks(File myFile) {

        
        try {

            this.setFile_path(
                    this.getTemplate_file_path(),
                    this.getTemplate_file_name());

            File templateFile = new File(this.getFile_path());

            Workbook template_Workbook = WorkbookFactory.create(templateFile);
            
            Workbook input_Workbook = WorkbookFactory.create(myFile);

            
            Sheet input_sheet
                    = input_Workbook.getSheet("TRANSIT_TIME_LANE");

            Sheet template_sheet
                    = template_Workbook.getSheet("TRANSIT_TIME_LANE");

            CopyRowsBetweenSheets(input_sheet, template_sheet);
          
            input_sheet = input_Workbook.getSheet("TRANSIT_LANE_DTL");

            template_sheet = template_Workbook.getSheet("TRANSIT_LANE_DTL");

            CopyRowsBetweenSheets(input_sheet, template_sheet);

            setFile_path(this.getOutput_file_folder(), myFile.getName());

            FileOutputStream out
                    = new FileOutputStream(new File(this.getFile_path()));
            template_Workbook.write(out);

            input_Workbook.close();

        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(
                    FileManagerService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(
                    FileManagerService.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    private void CopyRowsBetweenSheets(Sheet input_sheet, Sheet template_sheet) {
        for (Row row : input_sheet) {

            if (skipColumnsTitle(row)) {

                Row row_template_file
                        = template_sheet.createRow(row.getRowNum());

                for (Cell cell : row) {

                    Cell cell_template_file
                            = row_template_file
                                    .createCell(cell.getColumnIndex());

                    switch (cell.getCellType()) {

                        case STRING:
                            cell_template_file.
                                    setCellValue(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            cell_template_file.
                                    setCellValue(cell.getNumericCellValue());
                            break;

                        default:
                            break;

                    }

                }

            }

        }
    }

    private boolean skipColumnsTitle(Row row_input_file) {

        boolean isFirstRow = false;

        if (row_input_file.getRowNum() > 1) {

            isFirstRow = true;

        }

        return isFirstRow;
    }

    public String getExtension(String fileName) {

        return FilenameUtils.getExtension(fileName);
    }

}
