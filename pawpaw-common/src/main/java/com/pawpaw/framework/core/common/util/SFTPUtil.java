package com.pawpaw.framework.core.common.util;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class SFTPUtil {

    public static final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);

    public static final int SFTP_DEFAULT_PORT = 22;

    /**
     * 拉取单个文件
     *
     * @param host
     * @param port
     * @param fileName
     * @param userName
     * @param password
     * @param localFile
     * @throws Exception
     */
    public static void pullFile(String host, int port, List<String> remoteDirTree, String fileName, String userName,
                                String password, OutputStream localFile) throws Exception {

        pullFile(host, port, remoteDirTree, fileName, userName, password, localFile, 20000);

    }

    /**
     * 拉取单个文件
     *
     * @param host
     * @param port
     * @param remoteDir
     * @param fileName
     * @param userName
     * @param password
     * @param localFile
     * @param readTimeOut
     * @throws Exception
     */
    public static void pullFile(String host, int port, List<String> remoteDirTree, String fileName, String userName,
                                String password, OutputStream localFile, int readTimeOut) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = getChannel(host, port, userName, password, readTimeOut);

            if (remoteDirTree != null) {
                for (String dir : remoteDirTree) {
                    if (StringUtils.isNotBlank(dir)) {
                        channel.cd(dir);
                    }
                }
            }
            logger.info("begin get file {}", fileName);
            channel.get(fileName, localFile);
            logger.info("finish get file {}", fileName);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                closeChannel(channel);
            } catch (Exception e) {
                // swalow all exception
            }
            try {
                localFile.close();
            } catch (Exception e) {
                // swalow all exception
            }

        }

    }

    /**
     * 拉取目录
     *
     * @param host
     * @param port
     * @param remoteAbsoluteDir
     * @param userName
     * @param password
     * @param localDir
     * @return
     * @throws Exception
     */
    public static Collection<File> pullDir(String host, int port, String remoteAbsoluteDir, String userName,
                                           String password, File localDir) throws Exception {

        List<String> dirTree = new ArrayList<String>(1);
        dirTree.add(remoteAbsoluteDir);
        return pullDir(host, port, dirTree, userName, password, localDir, 20000);
    }

    /**
     * 拉取目录
     *
     * @param host
     * @param port
     * @param remoteAbsoluteDir
     * @param userName
     * @param password
     * @param localDir
     * @return
     * @throws Exception
     */
    public static Collection<File> pullDir(String host, int port, List<String> remoteDirTree, String userName,
                                           String password, File localDir) throws Exception {
        return pullDir(host, port, remoteDirTree, userName, password, localDir, 20000);
    }

    /**
     * 拉取目录
     *
     * @param host
     * @param port
     * @param remoteAbsoluteDir
     * @param userName
     * @param password
     * @param localDir
     * @return
     * @throws Exception
     */
    public static Collection<File> pullDir(String host, int port, List<String> remoteDirTree, String userName,
                                           String password, File localDir, int readTimeOut) throws Exception {

        ChannelSftp channel = null;
        try {

            if (!localDir.exists()) {
                localDir.mkdirs();
                logger.info("dir doesn't exist,crated:{}", localDir.getName());
            }
            if (!localDir.isDirectory()) {
                throw new IOException("local path is not a dir:" + localDir.getName());
            }
            Collection<File> localFiles = new HashSet<File>();
            channel = getChannel(host, port, userName, password, readTimeOut);

            for (String dir : remoteDirTree) {
                if (StringUtils.isNotBlank(dir)) {
                    channel.cd(dir);
                }

            }

            Vector<LsEntry> files = channel.ls(channel.pwd());
            for (LsEntry entry : files) {
                SftpATTRS attr = entry.getAttrs();
                if (!attr.isReg()) {
                    continue;
                }

                File child = new File(localDir, entry.getFilename());
                OutputStream childStream = new FileOutputStream(child);
                channel.get(entry.getFilename(), childStream);
                childStream.close();
                localFiles.add(child);
            }
            return localFiles;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                closeChannel(channel);
            } catch (Exception e) {
                // swalow all exception
            }

        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static ChannelSftp getChannel(String host, int port, String userName, String password, int timeout)
            throws JSchException {

        JSch jsch = new JSch(); // 创建JSch对象
        Session session = jsch.getSession(userName, host, port); // 根据用户名，主机ip，端口获取一个Session对象

        if (password != null) {
            session.setPassword(password); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        Channel channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接

        return (ChannelSftp) channel;
    }

    public static void closeChannel(ChannelSftp channel) throws Exception {
        if (channel == null) {
            return;
        }
        channel.disconnect();
        Session session = channel.getSession();
        if (session == null) {
            return;
        }

        session.disconnect();

    }

}
