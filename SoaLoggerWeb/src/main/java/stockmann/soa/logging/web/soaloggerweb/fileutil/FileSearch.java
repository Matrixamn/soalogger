/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb.fileutil;

import java.io.File;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author KATVTUO
 */
public class FileSearch {

    //Base path must come from either properties file or xml/Spring property 
    //injection
    private String basePath;
    private String env;
    private String interfaceName;
    private String pathSeparator;
    private String instanceId;
    private String filePath;
    private File file;
    private String fullPath;
    private String urlPath;
    private String baseUrl;


    public FileSearch(String envParam, String ifaceParam, String instIdParam) {
        
        this.env = envParam;
        this.interfaceName = ifaceParam;
        this.pathSeparator = System.getProperty("file.separator");
        this.instanceId = instIdParam;
       
    }

    public FileSearch() {
        this.pathSeparator = System.getProperty("file.separator");
    }
    /**
     * @return the basePath
     */

    public boolean checkFile() {
        this.filePath = this.createPaths();
        this.file = new File(this.filePath);
        if (file.isFile() && file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> searchFolder(String searchString) {
        StringTokenizer st = new StringTokenizer(interfaceName,"/");
        st.nextToken();
        interfaceName = st.nextToken();
        File folderSearch = new File(this.basePath + pathSeparator + env + pathSeparator + interfaceName);
        if (folderSearch.isDirectory()) {
            ArrayList<String> fileNames = new ArrayList<String>();
            File[] files = folderSearch.listFiles();
            for (File fil:files) {
                JavaGrep.compile(searchString);
                try {
                    if (JavaGrep.grep(fil)) {
                    StringTokenizer stt = new StringTokenizer(fil.getName(),".");
                    fileNames.add(stt.nextToken());
                    } else {

                    }
                } catch (Exception exp) {
                
                }
            }
            return fileNames;
        } else {
            return null;
        }
    }

    public int removeInterfaceMessageTo(Date to) {
        if (this.env != null && this.interfaceName != null) {
            ArrayList<File> deleteFiles = new ArrayList<File>();
            File folderSearch = new File(this.basePath + pathSeparator + env + pathSeparator + interfaceName);
            if (folderSearch.exists() && folderSearch.isDirectory()) {
               File[] files = folderSearch.listFiles();
               for (File fil:files) {
                   Date fileDate = new Date(fil.lastModified());
                   if (fileDate.before(to)) {
                       deleteFiles.add(fil);
                   }
               }

               for (File aFile:deleteFiles) {
                   aFile.delete();
               }
            } else {
                return -2;
            }

            return 0;
        } else {
            return -1;
        }
    }

    public int removeAllInterfaceMessages() {
        if (this.env != null && this.interfaceName != null) {
            File folderSearch = new File(this.basePath + pathSeparator + env + pathSeparator + interfaceName);
            if (folderSearch.exists() && folderSearch.isDirectory()) {
               File[] files = folderSearch.listFiles();
               for (File fil:files) {
                   fil.delete();

               }
               folderSearch.delete();
            } else {
                return -2;
            }

            return 0;
        } else {
            return -1;
        }
    }

    private String createPaths() {
        StringTokenizer st = new StringTokenizer(interfaceName,"/");
       
        interfaceName = st.nextToken();
        this.fullPath = basePath + pathSeparator +  env + pathSeparator + interfaceName + pathSeparator + instanceId + ".xml";
        this.urlPath = this.baseUrl + "/" + env + "/" + interfaceName + "/" + instanceId + ".xml";
        return fullPath;
    }

    public String getBasePath() {
        return basePath;
    }

    /**
     * @param basePath the basePath to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     * @return the env
     */
    public String getEnv() {
        return env;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * @return the interfaceName
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * @param interfaceName the interfaceName to set
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * @return the pathSeparator
     */
    public String getPathSeparator() {
        return pathSeparator;
    }

    /**
     * @param pathSeparator the pathSeparator to set
     */
    public void setPathSeparator(String pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * @return the fullPath
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * @return the urlPath
     */
    public String getUrlPath() {
        return urlPath;
    }

    /**
     * @param urlPath the urlPath to set
     */
    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }



}
