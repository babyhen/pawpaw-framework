package com.pawpaw.framework.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.List;

/**
 * windows pc机需要关闭防火墙
 *
 * @author liujixin
 */
public class FtpUtil {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    public static void pullFile(String host, int port, List<String> remoteDirTree, final String remoteFileName,
                                String userName, String password, OutputStream localFile) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(host, port);// 连接FTP服务器
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new ConnectException("connect ftp fail");
            }
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
                ftpClient.login(userName, password);
            }
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new RuntimeException("login ftp fail");
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (remoteDirTree != null) {
                for (String dir : remoteDirTree) {
                    ftpClient.changeWorkingDirectory(dir);
                }
            }
            FTPFile[] files = ftpClient.listFiles();
            if (files == null) {
                throw new IOException("remote file doesn't exist");
            }
            boolean isExist = false;
            for (FTPFile f : files) {
                if (StringUtils.equals(remoteFileName, f.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                throw new IOException("remote file doesn't exist: " + remoteFileName);
            }
            logger.info("begin get file {}", remoteFileName);
            ftpClient.retrieveFile(remoteFileName, localFile);
            logger.info("finish get file {}", remoteFileName);

            localFile.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }

    }

    public static void putFile(String host, int port, List<String> remoteDirTree, final String remoteFileName,
                               String userName, String password, InputStream input) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(host, port);// 连接FTP服务器
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new ConnectException("connect ftp fail");
            }
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
                ftpClient.login(userName, password);
            }
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new RuntimeException("login ftp fail");
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (remoteDirTree != null) {
                for (String dir : remoteDirTree) {
                    ftpClient.makeDirectory(dir);
                    ftpClient.changeWorkingDirectory(dir);
                }
            }

            boolean isSucc = ftpClient.storeFile(remoteFileName, input);
            if (!isSucc) {
                throw new IOException("put file fail");

            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }

            try {
                input.close();
            } catch (IOException e) {

            }
        }
    }
}
