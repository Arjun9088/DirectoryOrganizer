package com.arjun.organize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

public class DirectoryOrganizer {
	public static void main(String[] args) throws IOException {
		String newStringPdf = "C:\\Users\\USER\\Downloads\\pdfDir";
		String newStringZip = "C:\\Users\\USER\\Downloads\\zipDir";
		String newStringRar = "C:\\Users\\USER\\Downloads\\rarDir";
		String newStringJpg = "C:\\Users\\USER\\Downloads\\jpgDir";
		String newStringTxt = "C:\\Users\\USER\\Downloads\\txtDir";
		String newStringExe = "C:\\Users\\USER\\Downloads\\exeDir";
		String newStringExcel = "C:\\Users\\USER\\Downloads\\excelDir";



		String oldString = "C:\\Users\\USER\\Downloads";
		List<String> dirs = new ArrayList<>();
		List<String> dirList = Arrays.asList(newStringPdf, newStringZip, newStringRar, newStringJpg, newStringTxt, newStringExe, newStringExcel );
		DirectoryOrganizer directoryOrganizer = new DirectoryOrganizer();
		if (directoryOrganizer.createDirectory(dirList)) {

			directoryOrganizer.moveFiles(dirList,oldString);
		}

	}

	public boolean createDirectory(List<String> dirList) throws IOException {
		for(String paths: dirList)
		{
			Path path = Paths.get(paths);
			if(!Files.exists(path))
			{
				Files.createDirectory(path);
			}
		}
		return true;

	}
	
	public boolean moveFiles(List<String> newDir, String oldDir) throws IOException
	{
		Path oldPath = Paths.get(oldDir);
		List<Path> pdfPaths = Files.list(oldPath)
				.filter(n -> n.getFileName().toString().endsWith("pdf"))
				.collect(Collectors.toList());
		List<Path> allPaths = Files.list(oldPath).collect(Collectors.toList());
		Map<String, List<Path>> pathMap = new LinkedHashMap<String, List<Path>>();
		List<Path> pdfList = new ArrayList<Path>();
		List<Path> zipList = new ArrayList<Path>();
		List<Path> rarList = new ArrayList<Path>();
		List<Path> jpgList = new ArrayList<Path>();
		List<Path> exeList = new ArrayList<Path>();
		List<Path> txtList = new ArrayList<Path>();
		List<Path> excelList = new ArrayList<Path>();
		for(Path p: allPaths)
		{
			if(!Files.isDirectory(p))
			{
				switch (FilenameUtils.getExtension(p.getFileName().toString().toLowerCase())) {
				case "pdf":
					pdfList.add(p);
					break;
				case "zip":;
				case "7z":
					zipList.add(p);
					break;
				case "rar":;
				case "iso":
					rarList.add(p);
					break;
				case "jpg":;
				case "jpeg":;
				case "img":
					jpgList.add(p);
					break;
				case "txt":
					txtList.add(p);
					break;
				case "exe":
					exeList.add(p);
					break;
				case "xls":;
				case "xlsx":;
				case "csv":;
				case "odf":
					excelList.add(p);
					break;
				default:
					break;
				}
			}
		}
		pathMap.put("pdf", pdfList);
		pathMap.put("zip", zipList);
		pathMap.put("rar", rarList);
		pathMap.put("jpg", jpgList);
		pathMap.put("excel", excelList);
		pathMap.put("pdf", pdfList);
		pathMap.put("txt", txtList);
		for(String paths : newDir)
		{
			Path newPath = Paths.get(paths);
			if(newPath.getFileName().toString().equalsIgnoreCase("pdfdir"))
				move(pdfList, newPath, oldDir);
			if(newPath.getFileName().toString().equalsIgnoreCase("zipdir"))
				move(zipList, newPath, oldDir);
			if(newPath.getFileName().toString().equalsIgnoreCase("rardir"))
				move(rarList, newPath, oldDir);
			if(newPath.getFileName().toString().equalsIgnoreCase("jpgdir"))
				move(jpgList, newPath, oldDir);
			if(newPath.getFileName().toString().equalsIgnoreCase("txtdir"))
				move(txtList, newPath, oldDir);
			if(newPath.getFileName().toString().equalsIgnoreCase("exedir"))
				move(exeList, newPath, oldDir);
			if(newPath.getFileName().toString().equalsIgnoreCase("exceldir"))
				move(excelList, newPath, oldDir);
			
		}
		return true;
	}
	
	public void move(List<Path> pathList, Path newPath, String oldPath)
	{
		for(Path p: pathList)
		{
			Path oldFullPath = Paths.get(oldPath+ "\\" +p.getFileName().toString().trim());
			Path newFullPath = Paths.get(newPath+"\\"+p.getFileName().toString().trim());
			try{
				Path temp = Files.move(oldFullPath, newFullPath);
				if(temp!=null)
				{
					System.out.println(oldFullPath.getFileName().toString() + " has been moved to " + newFullPath);
				}
				
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}


