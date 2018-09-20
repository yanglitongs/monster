package com.ylt.wechat.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/** 
 * �����з�װһЩ���õ��ļ������� 
 * ���з������Ǿ�̬����������Ҫ���ɴ����ʵ���� 
 * Ϊ�������ɴ����ʵ�������췽��������Ϊprivate���͵ġ� 
 * @since  0.1 
 */ 
public class FileUtils {
	 /** 
     * ˽�й��췽������ֹ���ʵ��������Ϊ�����಻��Ҫʵ������ 
     */  
    private FileUtils()  
    {  
  
    }  
    /** 
     * ������Ҫ�����ļ��� 
     *  
     * @param dirPath 
     *            �ļ���·�� 
     * @param del 
     *            �����ļ����Ƿ�ɾ�� 
     */  
    public static void mkdir(String dirPath, boolean del) {  
        File dir = new File(dirPath);  
        if (dir.exists()) {  
            if (del)  
                dir.delete();  
            else  
                return;  
        }  
        if (!dir.getParentFile().exists()) {  
            dir.getParentFile().mkdirs();  
        }  
    }  
  
   
    public static boolean expiredFile(Long modifiedTimeMS,int minutes) {  
        long maxMillis=modifiedTimeMS + minutes*60*1000;  
        long nowMillis=System.currentTimeMillis();  
        return maxMillis< nowMillis;   
    }  
     
    /** 
     * �޸��ļ���������ʱ�䡣 
     * ����ļ��������򴴽����ļ��� 
     * <b>Ŀǰ�����������Ϊ��ʽ�����ȶ�����Ҫ�Ƿ�����Щ��Ϣ�������Щ��Ϣ����Ƿ������ڿ����С�</b> 
     * @param file ��Ҫ�޸�������ʱ����ļ��� 
     * @since  0.1 
     */  
    public static void touch(File file)  
    {  
        long currentTime = System.currentTimeMillis();  
        if (!file.exists())  
        {  
            try  
            {  
                if (!file.getParentFile().exists()) {  
                    file.getParentFile().mkdirs();  
                }  
            }  
            catch (Exception e)  
            {  
                System.err.println("Create file failed!");  
                e.printStackTrace();  
            }  
        }  
        boolean result = file.setLastModified(currentTime);  
        if (!result)  
        {  
            System.err.println("touch failed: " + file.getName());  
        }  
    }  
  
    /** 
     * �޸��ļ���������ʱ�䡣 
     * ����ļ��������򴴽����ļ��� 
     * <b>Ŀǰ�����������Ϊ��ʽ�����ȶ�����Ҫ�Ƿ�����Щ��Ϣ�������Щ��Ϣ����Ƿ������ڿ����С�</b> 
     * @param fileName ��Ҫ�޸�������ʱ����ļ����ļ����� 
     * @since  0.1 
     */  
    public static void touch(String fileName)  
    {  
        File file = new File(fileName);  
        touch(file);  
    }  
  
    /** 
     * �޸��ļ���������ʱ�䡣 
     * ����ļ��������򴴽����ļ��� 
     * <b>Ŀǰ�����������Ϊ��ʽ�����ȶ�����Ҫ�Ƿ�����Щ��Ϣ�������Щ��Ϣ����Ƿ������ڿ����С�</b> 
     * @param files ��Ҫ�޸�������ʱ����ļ����顣 
     * @since  0.1 
     */  
    public static void touch(File[] files)  
    {  
        for (int i = 0; i < files.length; i++)  
        {  
            touch(files[i]);  
        }  
    }  
  
    /** 
     * �޸��ļ���������ʱ�䡣 
     * ����ļ��������򴴽����ļ��� 
     * <b>Ŀǰ�����������Ϊ��ʽ�����ȶ�����Ҫ�Ƿ�����Щ��Ϣ�������Щ��Ϣ����Ƿ������ڿ����С�</b> 
     * @param fileNames ��Ҫ�޸�������ʱ����ļ������顣 
     * @since  0.1 
     */  
    public static void touch(String[] fileNames)  
    {  
        File[] files = new File[fileNames.length];  
        for (int i = 0; i < fileNames.length; i++)  
        {  
            files[i] = new File(fileNames[i]);  
        }  
        touch(files);  
    }  
  
    /** 
     * �ж�ָ�����ļ��Ƿ���ڡ� 
     * @param fileName Ҫ�жϵ��ļ����ļ��� 
     * @return ����ʱ����true�����򷵻�false�� 
     * @since  0.1 
     */  
    public static boolean isFileExist(String fileName)  
    {  
          
        return new File(fileName).exists();  
    }  
  
    /** 
     * ����ָ����Ŀ¼�� 
     * ���ָ����Ŀ¼�ĸ�Ŀ¼�������򴴽���Ŀ¼����������Ҫ�ĸ�Ŀ¼�� 
     * <b>ע�⣺���ܻ��ڷ���false��ʱ�򴴽����ָ�Ŀ¼��</b> 
     * @param file Ҫ������Ŀ¼ 
     * @return ��ȫ�����ɹ�ʱ����true�����򷵻�false�� 
     * @since  0.1 
     */  
    public static boolean makeDirectory(File file)  
    {  
        //File parent = file.getParentFile();  
        //if (parent != null)   
        //{  
        return file.mkdirs();  
        //}  
        //return false;  
    }  
  
    /** 
     * ����ָ����Ŀ¼�� 
     * ���ָ����Ŀ¼�ĸ�Ŀ¼�������򴴽���Ŀ¼����������Ҫ�ĸ�Ŀ¼�� 
     * <b>ע�⣺���ܻ��ڷ���false��ʱ�򴴽����ָ�Ŀ¼��</b> 
     * @param fileName Ҫ������Ŀ¼��Ŀ¼�� 
     * @return ��ȫ�����ɹ�ʱ����true�����򷵻�false�� 
     * @since  0.1 
     */  
    public static boolean makeDirectory(String fileName)  
    {  
        boolean bTmp = false;  
        try  
        {  
            File file = new File(fileName.toString());  
            makeDirectory(file);  
            bTmp = true;  
        }  
        catch (Exception ex)  
        {  
            bTmp = false;  
            System.out.println(ex.toString());  
        }  
        return bTmp;  
    }  
  
    /** 
     * ���ָ��Ŀ¼�е��ļ��� 
     * ���������������ɾ�����е��ļ�������ֻҪ��һ���ļ�û�б�ɾ�����᷵��false�� 
     * ������������������ɾ����������ɾ����Ŀ¼�������ݡ� 
     * @param directory Ҫ��յ�Ŀ¼ 
     * @return Ŀ¼�µ������ļ������ɹ�ɾ��ʱ����true�����򷵻�false. 
     * @since  0.1 
     */  
    public static boolean emptyDirectory(File directory)  
    {  
        boolean result = false;  
        File[] entries = directory.listFiles();  
        for (int i = 0; i < entries.length; i++)  
        {  
            if (!entries[i].delete())  
            {  
                result = false;  
            }  
        }  
        return result;  
    }  
  
    /** 
     * ���ָ��Ŀ¼�е��ļ��� 
     * ���������������ɾ�����е��ļ�������ֻҪ��һ���ļ�û�б�ɾ�����᷵��false�� 
     * ������������������ɾ����������ɾ����Ŀ¼�������ݡ� 
     * @param directoryName Ҫ��յ�Ŀ¼��Ŀ¼�� 
     * @return Ŀ¼�µ������ļ������ɹ�ɾ��ʱ����true�����򷵻�false�� 
     * @since  0.1 
     */  
    public static boolean emptyDirectory(String directoryName)  
    {  
        File dir = new File(directoryName);  
        return emptyDirectory(dir);  
    }  
  
    /** 
     * ɾ��ָ��Ŀ¼�����е��������ݡ� 
     * @param dirName Ҫɾ����Ŀ¼��Ŀ¼�� 
     * @return ɾ���ɹ�ʱ����true�����򷵻�false�� 
     * @since  0.1 
     */  
    public static boolean deleteDirectory(String dirName)  
    {  
        return deleteDirectory(new File(dirName));  
    }  
  
    /** 
     * ɾ��ָ��Ŀ¼�����е��������ݡ� 
     * @param dir Ҫɾ����Ŀ¼ 
     * @return ɾ���ɹ�ʱ����true�����򷵻�false�� 
     * @since  0.1 
     */  
    public static boolean deleteDirectory(File dir)  
    {  
        if ((dir == null) || !dir.isDirectory()) { throw new IllegalArgumentException("Argument "  
                + dir + " is not a directory. "); }  
  
        File[] entries = dir.listFiles();  
        int sz = entries.length;  
  
        for (int i = 0; i < sz; i++)  
        {  
            if (entries[i].isDirectory())  
            {  
                if (!deleteDirectory(entries[i])) { return false; }  
            }  
            else  
            {  
                if (!entries[i].delete()) { return false; }  
            }  
        }  
  
        if (!dir.delete()) { return false; }  
        return true;  
    }  
  
    /** 
     * �г�Ŀ¼�е��������ݣ���������Ŀ¼�е����ݡ� 
     * @param fileName Ҫ�г���Ŀ¼��Ŀ¼�� 
     * @return Ŀ¼���ݵ��ļ����顣 
     * @since  0.1 
     */  
    public static File[] listAll(String fileName)  
    {  
        return listAll(new File(fileName));  
    } 
    /** 
     * �г�Ŀ¼�е��������ݣ���������Ŀ¼�е����ݡ� 
     * @param file Ҫ�г���Ŀ¼ 
     * @return Ŀ¼���ݵ��ļ����顣 
     * @since  0.1 
     */  
    public static File[] listAll(File file)  
    {  
    	/*
        ArrayList<File> list = new ArrayList<File>();  
        File[] files;  
        if (!file.exists() || file.isFile()) 
        { return null; }  
        list(list, file,new FileFilter() {  
            public boolean accept(File file) {  
                //if the file extension is .txt return true, else false  
                  return true;  
            }  
            @Override
            public String getDescription(){  
                // TODO Auto-generated method stub  
                return null;  
            }
        });  
        list.remove(file);  
        files = new File[list.size()];  
        list.toArray(files);  
        return files;
        */
    	return new File[0];
    }  
  
    /** 
     * �г�Ŀ¼�е��������ݣ���������Ŀ¼�е����ݡ� 
     * @param file Ҫ�г���Ŀ¼ 
     * @param filter ������ 
     * @return Ŀ¼���ݵ��ļ����顣 
     * @since  0.1 
     */  
    public static File[] listAll(File file,  
            javax.swing.filechooser.FileFilter filter)  
    {  
        ArrayList<File> list = new ArrayList<File>();  
        File[] files;  
        if (!file.exists() || file.isFile()) { return null; }  
        list(list, file, filter);  
        files = new File[list.size()];  
        list.toArray(files);  
        return files;  
    }  
  
    /** 
     * ��Ŀ¼�е�������ӵ��б� 
     * @param list �ļ��б� 
     * @param filter ������ 
     * @param file Ŀ¼ 
     */  
    private static void list(ArrayList<File> list, File file,  
            javax.swing.filechooser.FileFilter filter)  
    {  
        if (filter.accept(file))  
        {  
            list.add(file);  
            if (file.isFile()) { return; }  
        }  
        if (file.isDirectory())  
        {  
            File files[] = file.listFiles();  
            for (int i = 0; i < files.length; i++)  
            {  
                list(list, files[i], filter);  
            }  
        }  
  
    }  
  
    /** 
     * �����ļ���URL��ַ�� 
     * @param file �ļ� 
     * @return �ļ���Ӧ�ĵ�URL��ַ 
     * @throws MalformedURLException 
     * @since  0.4 
     * @deprecated ��ʵ�ֵ�ʱ��û��ע�⵽File�౾���һ��toURL�������ļ�·��ת��ΪURL�� 
     *             ��ʹ��File.toURL������ 
     */  
    public static URL getURL(File file) throws MalformedURLException  
    {  
        String fileURL = "file:/" + file.getAbsolutePath();  
        URL url = new URL(fileURL);  
        return url;  
    }  
  
    /** 
     * ���ļ�·���õ��ļ����� 
     * @param filePath �ļ���·�������������·��Ҳ�����Ǿ���·�� 
     * @return ��Ӧ���ļ��� 
     * @since  0.4 
     */  
    public static String getFileName(String filePath)  
    {  
        File file = new File(filePath);  
        return file.getName();  
    }  
  
    /** 
     * ���ļ����õ��ļ�����·���� 
     * @param fileName �ļ��� 
     * @return ��Ӧ���ļ�·�� 
     * @since  0.4 
     */  
    public static String getFilePath(String fileName)  
    {  
        File file = new File(fileName);  
        return file.getAbsolutePath();  
    }  
  
    /** 
     * ��DOS/Windows��ʽ��·��ת��ΪUNIX/Linux��ʽ��·���� 
     * ��ʵ���ǽ�·���е�"\"ȫ����Ϊ"/"����Ϊ��ĳЩ���������ת��Ϊ���ַ�ʽ�ȽϷ��㣬 
     * ĳ�г̶���˵"/"��"\"���ʺ���Ϊ·���ָ���������DOS/WindowsҲ��������·���ָ����� 
     * @param filePath ת��ǰ��·�� 
     * @return ת�����·�� 
     * @since  0.4 
     */  
    public static String toUNIXpath(String filePath)  
    {  
        return filePath.replace('\\', '/');  
    }  
  
    /** 
     * ���ļ����õ�UNIX�����ļ�����·���� 
     * @param fileName �ļ��� 
     * @return ��Ӧ��UNIX�����ļ�·�� 
     * @since  0.4 
     * @see #toUNIXpath(String filePath) toUNIXpath 
     */  
    public static String getUNIXfilePath(String fileName)  
    {  
        File file = new File(fileName);  
        return toUNIXpath(file.getAbsolutePath());  
    }  
  
    /** 
     * �õ��ļ������͡� 
     * ʵ���Ͼ��ǵõ��ļ��������һ����.������Ĳ��֡� 
     * @param fileName �ļ��� 
     * @return �ļ����е����Ͳ��� 
     * @since  0.5 
     */  
    public static String getTypePart(String fileName)  
    {  
        int point = fileName.lastIndexOf('.');  
        int length = fileName.length();  
        if (point == -1 || point == length - 1)  
        {  
            return "";  
        }  
        else  
        {  
            return fileName.substring(point + 1, length);  
        }  
    }  
  
    /** 
     * �õ��ļ������͡� 
     * ʵ���Ͼ��ǵõ��ļ��������һ����.������Ĳ��֡� 
     * @param file �ļ� 
     * @return �ļ����е����Ͳ��� 
     * @since  0.5 
     */  
    public static String getFileType(File file)  
    {  
        return getTypePart(file.getName());  
    }  
  
    /** 
     * �õ��ļ������ֲ��֡� 
     * ʵ���Ͼ���·���е����һ��·���ָ�����Ĳ��֡� 
     * @param fileName �ļ��� 
     * @return �ļ����е����ֲ��� 
     * @since  0.5 
     */  
    public static String getNamePart(String fileName)  
    {  
        int point = getPathLastIndex(fileName);  
        int length = fileName.length();  
        if (point == -1)  
        {  
            return fileName;  
        }  
        else if (point == length - 1)  
        {  
            int secondPoint = getPathLastIndex(fileName, point - 1);  
            if (secondPoint == -1)  
            {  
                if (length == 1)  
                {  
                    return fileName;  
                }  
                else  
                {  
                    return fileName.substring(0, point);  
                }  
            }  
            else  
            {  
                return fileName.substring(secondPoint + 1, point);  
            }  
        }  
        else  
        {  
            return fileName.substring(point + 1);  
        }  
    }  
  
    /** 
     * �õ��ļ����еĸ�·�����֡� 
     * ������·���ָ�������Ч�� 
     * ������ʱ����""�� 
     * ����ļ�������·���ָ�����β���򲻿��Ǹ÷ָ���������"/path/"����""�� 
     * @param fileName �ļ��� 
     * @return ��·���������ڻ����Ѿ��Ǹ�Ŀ¼ʱ����"" 
     * @since  0.5 
     */  
    public static String getPathPart(String fileName)  
    {  
        int point = getPathLastIndex(fileName);  
        int length = fileName.length();  
        if (point == -1)  
        {  
            return "";  
        }  
        else if (point == length - 1)  
        {  
            int secondPoint = getPathLastIndex(fileName, point - 1);  
            if (secondPoint == -1)  
            {  
                return "";  
            }  
            else  
            {  
                return fileName.substring(0, secondPoint);  
            }  
        }  
        else  
        {  
            return fileName.substring(0, point);  
        }  
    }  
  
    /** 
     * �õ�·���ָ������ļ�·�����״γ��ֵ�λ�á� 
     * ����DOS����UNIX���ķָ��������ԡ� 
     * @param fileName �ļ�·�� 
     * @return ·���ָ�����·�����״γ��ֵ�λ�ã�û�г���ʱ����-1�� 
     * @since  0.5 
     */  
    public static int getPathIndex(String fileName)  
    {  
        int point = fileName.indexOf('/');  
        if (point == -1)  
        {  
            point = fileName.indexOf('\\');  
        }  
        return point;  
    }  
  
    /** 
     * �õ�·���ָ������ļ�·����ָ��λ�ú��״γ��ֵ�λ�á� 
     * ����DOS����UNIX���ķָ��������ԡ� 
     * @param fileName �ļ�·�� 
     * @param fromIndex ��ʼ���ҵ�λ�� 
     * @return ·���ָ�����·����ָ��λ�ú��״γ��ֵ�λ�ã�û�г���ʱ����-1�� 
     * @since  0.5 
     */  
    public static int getPathIndex(String fileName, int fromIndex)  
    {  
        int point = fileName.indexOf('/', fromIndex);  
        if (point == -1)  
        {  
            point = fileName.indexOf('\\', fromIndex);  
        }  
        return point;  
    }  
  
    /** 
     * �õ�·���ָ������ļ�·���������ֵ�λ�á� 
     * ����DOS����UNIX���ķָ��������ԡ� 
     * @param fileName �ļ�·�� 
     * @return ·���ָ�����·���������ֵ�λ�ã�û�г���ʱ����-1�� 
     * @since  0.5 
     */  
    public static int getPathLastIndex(String fileName)  
    {  
        int point = fileName.lastIndexOf('/');  
        if (point == -1)  
        {  
            point = fileName.lastIndexOf('\\');  
        }  
        return point;  
    }  
  
    /** 
     * �õ�·���ָ������ļ�·����ָ��λ��ǰ�����ֵ�λ�á� 
     * ����DOS����UNIX���ķָ��������ԡ� 
     * @param fileName �ļ�·�� 
     * @param fromIndex ��ʼ���ҵ�λ�� 
     * @return ·���ָ�����·����ָ��λ��ǰ�����ֵ�λ�ã�û�г���ʱ����-1�� 
     * @since  0.5 
     */  
    public static int getPathLastIndex(String fileName, int fromIndex)  
    {  
        int point = fileName.lastIndexOf('/', fromIndex);  
        if (point == -1)  
        {  
            point = fileName.lastIndexOf('\\', fromIndex);  
        }  
        return point;  
    }  
  
    /** 
     * ���ļ����е����Ͳ���ȥ���� 
     * @param filename �ļ��� 
     * @return ȥ�����Ͳ��ֵĽ�� 
     * @since  0.5 
     */  
    public static String trimType(String filename)  
    {  
        int index = filename.lastIndexOf(".");  
        if (index != -1)  
        {  
            return filename.substring(0, index);  
        }  
        else  
        {  
            return filename;  
        }  
    }  
  
    /** 
     * �õ����·���� 
     * �ļ�������Ŀ¼�����ӽڵ�ʱ�����ļ����� 
     * @param pathName Ŀ¼�� 
     * @param fileName �ļ��� 
     * @return �õ��ļ��������Ŀ¼�������·����Ŀ¼�²����ڸ��ļ�ʱ�����ļ��� 
     * @since  0.5 
     */  
    public static String getSubpath(String pathName, String fileName)  
    {  
        int index = fileName.indexOf(pathName);  
        if (index != -1)  
        {  
            return fileName.substring(index + pathName.length() + 1);  
        }  
        else  
        {  
            return fileName;  
        }  
    }  
  
    /** 
     * �����ļ��� 
     * @param fromFileName Դ�ļ��� 
     * @param toFileName Ŀ���ļ��� 
     * @return �ɹ������ļ�ʱ����true�����򷵻�false 
     * @since  0.6 
     */  
    public static boolean copy(String fromFileName, String toFileName)  
    {  
        return copy(fromFileName, toFileName, false);  
    }  
  
    /** 
     * �����ļ��� 
     * @param fromFileName Դ�ļ��� 
     * @param toFileName Ŀ���ļ��� 
     * @param override Ŀ���ļ�����ʱ�Ƿ񸲸� 
     * @return �ɹ������ļ�ʱ����true�����򷵻�false 
     * @since  0.6 
     */  
    public static boolean copy(String fromFileName, String toFileName,  
            boolean override)  
    {  
        File fromFile = new File(fromFileName);  
        File toFile = new File(toFileName);  
  
        if (!fromFile.exists() || !fromFile.isFile() || !fromFile.canRead()) { return false; }  
  
        if (toFile.isDirectory())  
        {  
            toFile = new File(toFile, fromFile.getName());  
  
        }  
        if (toFile.exists())  
        {  
            if (!toFile.canWrite() || override == false) { return false; }  
        }  
        else  
        {  
            String parent = toFile.getParent();  
            if (parent == null)  
            {  
                parent = System.getProperty("user.dir");  
            }  
            File dir = new File(parent);  
            if (!dir.exists() || dir.isFile() || !dir.canWrite()) { return false; }  
        }  
  
        FileInputStream from = null;  
        FileOutputStream to = null;  
        try  
        {  
            from = new FileInputStream(fromFile);  
            to = new FileOutputStream(toFile);  
            byte[] buffer = new byte[4096];  
            int bytes_read;  
            while ((bytes_read = from.read(buffer)) != -1)  
            {  
                to.write(buffer, 0, bytes_read);  
            }  
            return true;  
        }  
        catch (IOException e)  
        {  
            return false;  
        }  
        finally  
        {  
            if (from != null)  
            {  
                try  
                {  
                    from.close();  
                }  
                catch (IOException e)  
                {  
                    ;  
                }  
            }  
            if (to != null)  
            {  
                try  
                {  
                    to.close();  
                }  
                catch (IOException e)  
                {  
                    ;  
                }  
            }  
        }  
    }  
    public static String getExtensionName(String filename) {  
        return filename.substring(filename.lastIndexOf('.'));  
    }  
        /** 
         * �ļ����� 
         * @author yulong 
         * @description 
         * @date 2012-8-29  
         * @param src 
         * @param dst 
         * @param BUFFER_SIZE 
         * @return 
         */  
        public static boolean copy(File src, File dst, int BUFFER_SIZE) {  
            boolean result = false;  
            InputStream in = null;  
            OutputStream out = null;  
            try {  
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);  
                out = new BufferedOutputStream(new FileOutputStream(dst),  
                        BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                int len = 0;  
                while ((len = in.read(buffer)) > 0) {  
                    out.write(buffer, 0, len);  
                }  
                result = true;  
            } catch (Exception e) {  
                e.printStackTrace();  
                result = false;  
            } finally {  
                if (null != in) {  
                    try {  
                        in.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
                if (null != out) {  
                    try {  
                        out.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            return result;  
        }  
        public static String getFileContents(File file) {  
             StringBuilder result = new StringBuilder();  
            try{  
                BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�  
                String s = null;  
                while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��  
                    result.append(s).append("\n");  
                }  
                br.close();      
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return result.toString();  
        }  
          
        public static boolean copy(InputStream in, File dst, int BUFFER_SIZE) {  
            boolean result = false;  
            OutputStream out = null;  
            try {  
                out = new BufferedOutputStream(new FileOutputStream(dst),  
                        BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                int len = 0;  
                while ((len = in.read(buffer)) > 0) {  
                    out.write(buffer, 0, len);  
                }  
                result = true;  
            } catch (Exception e) {  
                e.printStackTrace();  
                result = false;  
            } finally {  
                if (null != in) {  
                    try {  
                        in.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
                if (null != out) {  
                    try {  
                        out.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            return result;  
        }  
    /**  
     * д�ļ�������  
     * @param in  
     * @param fileName  
     * @throws IOException  
     */    
    public static void copyFile(InputStream in,String fileName,String basePath) throws IOException{  
        FileUtils.mkdir(basePath    
                  + fileName, true);  
        FileOutputStream fs = new FileOutputStream(basePath    
                  + fileName);    
          byte[] buffer = new byte[1024 * 1024];   
          int byteread = 0;    
          while ((byteread = in.read(buffer)) != -1) {    
              fs.write(buffer, 0, byteread);    
              fs.flush();    
          }    
          fs.close();    
          in.close();    
    }    
    // �����ļ���  
    public void createFold(String path) {  
        try {  
            File folder = new File(path);  
            if (!folder.exists()) {  
                folder.mkdirs();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * ���ĳ���ļ��ľ���·�� 
     * @param x 
     * @return 
     */  
    public static String getPath(String x)  
    {  
        String path = "";  
        try  
        {  
            File file = new File(".");  
            path = file.getCanonicalPath() + "\\" + x + "\\";  
        }  
        catch (IOException e)  
        {  
            System.out.println("com.job5156.util.FileUtil getPath()  error :"  
                    + e.getMessage());  
        }  
        return path;  
    }  
      
    public static boolean existFile(String flePath){  
      
        return existFile(new File(flePath));          
    }  
      
    public static boolean existFile(File file){  
        if(file == null)  
            return false;  
        try {  
            return file.exists();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
  
    /** 
     * ���·�� 
     * @return  ��Ŀ¼��java/jspӦ�ó���ĸ�Ŀ¼ 
     * @author Lzj.Liu 
     */  
    public static String getPath()  
    {  
        String path = "";  
        File file = new File(".");  
        try  
        {  
            path = file.getCanonicalPath();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        return path;  
    }  
  
    /** 
     * �������ϼ�(����ͨ��) 
     * @param folderName �ļ������� 
     * @return boolean true:ok;false:faild 
     */  
    public static boolean createFolder(String folderName)  
    {  
        boolean bTmp = false;  
        try  
        {  
            folderName = folderName.toString();  
            File file = new File(folderName);  
            if(!file.exists()){  
                file.mkdirs();  
            }  
            bTmp = true;  
        }  
        catch (Exception ex)  
        {  
            System.out.println(ex.toString());  
            bTmp = false;  
        }  
        return bTmp;  
    }  
  
    /** 
     * ɾ��ָ�����ļ� 
     * @param fileName ָ�����ļ�·�����ļ��� 
     * @return boolean true:�ɹ�;false:ʧ�� 
     */  
    public static boolean deleteFile(String fileName)  
    {  
        boolean bTmp = false;  
        try  
        {  
            fileName = fileName.toString();  
            File file = new File(fileName);  
            if (file.exists())  
            {  
                file.delete();  
                bTmp = true;  
            }  
            else  
            {  
                System.out.println(fileName + "�ļ�·������!");  
                bTmp = false;  
            }  
        }  
        catch (Exception ex)  
        {  
            bTmp = false;  
            System.out.println("FileUtil->deleteFile" + ex.toString());  
        }  
        return bTmp;  
    }  
  
    /** 
     * ����ָ�����ļ� 
     * @param fileName �ļ�·�����ļ���(��:c:\\a.txt); 
     * @return boolean true:�ɹ�;false:ʧ�� 
     */  
    public static boolean createFile(String filePath)  
    {  
        boolean bTmp = false;  
        filePath = filePath.toString();  
        File myFilePath = new File(filePath);  
        FileWriter resultFile = null;  
        try  
        {  
            if (!myFilePath.exists()) myFilePath.createNewFile();  
            resultFile = new FileWriter(myFilePath);  
            /*PrintWriter myFile = */new PrintWriter(resultFile);  
        }  
        catch (Exception ex)  
        {  
            System.out.println(ex.toString());  
        }  
        finally  
        {  
            try  
            {  
                resultFile.close();  
            }  
            catch (IOException ioe)  
            {  
                System.out.println(ioe.toString());  
            }  
        }  
        return bTmp;  
    }  
  
  
    /** 
     * ���·�� 
     * @return ��Ŀ¼ 
     */  
    public static String getRootPath()  
    {  
        return getPath();  
    }  
  
    public static void main(String[] args)  
    {  
        //boolean b=createFolder("uploadpic\\2002515\\xxx.gif");  
        //System.out.println(b);  
        //boolean bTmp=isFileExist("WEB-INF\\src\\com\\job5156\\util\\FileUtil.java");  
        //System.out.println(bTmp);  
        //System.out.println(getPath());  
        //makeDirectory("uploadpic\\200515\\xxxzzz.gif");  
        //deleteDirectory("uploadpic\\200515\\xxxzzz.gif");  
        //copy("uploadpic\\123.gif","uploadpic\\xxxyy.gif\\xxx.gif",true);  
        //emptyDirectory("uploadpic\\200515");  
        System.out.println("---:"+FileUtils.getFileContents(new File("/Users/yulong/git/myutils/src/main/java/com/yulon/util/DesUtil.java")));  
    }  
}