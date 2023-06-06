/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falabella.main;

import com.falabella.services.FileManagerService;
import org.apache.commons.io.FileUtils;
import java.io.File;


/**
 *
 * @author ext_ealinares
 */
public class Program {

    private static FileManagerService fms;

    public static void main(String[] args) throws Exception {
        fms = new FileManagerService();
        File folder = new File(fms.getInput_file_folder());
        listFilesForFolder(folder);
        FileUtils.cleanDirectory(folder);
    }

    public static void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            }
            else {
                doFileProcess(fileEntry);
            }
        }
    }

    public static void doFileProcess(File fileEntry) {
        String ext = fms.getExtension(fileEntry.getAbsolutePath());
        if (ext.equals("csv")) {
            moveCsvFile(fileEntry);
        }
        else if (ext.equals("xls")) {
            CopyDataBetweenFiles(fileEntry);
        }
    }

    private static void moveCsvFile(File myFile) {
        System.out.println(
                String.format("Moving csv file : %S ", myFile.getName()));
        fms.setFile_path(fms.getOutput_file_folder(), myFile.getName());
        myFile.renameTo(new File(fms.getFile_path()));
    }

    private static void CopyDataBetweenFiles(File myFile) {
       
            System.out.println(String.format("Copy xls File : %S ", myFile.getName()));
            fms.CopyDataBetweenWorkbooks(myFile);   
            

    }
}
