package qla.modules.confuguration;

import qla.modules.confuguration.AppConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConcurrencyHelper
{
    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        System.out.println("number_of_cores = " + getNumberOfCPUCores());
        System.out.println((System.currentTimeMillis() - start) / 1000. + " seconds");
    }

    public static int getNumberOfCPUCores()
    {
        String nThreadsPropertyStrValue = AppConfiguration.getProperty("nThreads");
        if (nThreadsPropertyStrValue!=null){
            return Integer.parseInt(nThreadsPropertyStrValue);
        }
        int coresAmountFromOS = getCoresAmountFromOS();
        AppConfiguration.setProperty("nThreads",String.valueOf(coresAmountFromOS));
        return coresAmountFromOS;
    }

    private static int getCoresAmountFromOS()
    {
        OsValidator osValidator = new OsValidator();
        String command = "";
        if (osValidator.isMac())
        {
            command = "sysctl -n machdep.cpu.core_count";
        }
        else if (osValidator.isUnix())
        {
            command = "lscpu";
        }
        else if (osValidator.isWindows())
        {
            command = "cmd /C WMIC CPU Get /Format:List";
        }
        Process process = null;
        int numberOfCores = 0;
        int sockets = 0;
        try
        {
            if (osValidator.isMac())
            {
                String[] cmd = {"/bin/sh", "-c", command};
                process = Runtime.getRuntime().exec(cmd);
            }
            else
            {
                process = Runtime.getRuntime().exec(command);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;

        try
        {
            while ((line = reader.readLine()) != null)
            {
                if (osValidator.isMac())
                {
                    numberOfCores = line.length() > 0 ? Integer.parseInt(line) : 0;
                }
                else if (osValidator.isUnix())
                {
                    if (line.contains("Core(s) per socket:"))
                    {
                        numberOfCores = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
                    }
                    if (line.contains("Socket(s):"))
                    {
                        sockets = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
                    }
                }
                else if (osValidator.isWindows())
                {
                    if (line.contains("NumberOfCores"))
                    {
                        numberOfCores = Integer.parseInt(line.split("=")[1]);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (osValidator.isUnix())
        {
            return numberOfCores * sockets;
        }
        return numberOfCores;
    }

    public static class OsValidator
    {
        private String os = System.getProperty("os.name").toLowerCase();

        boolean isWindows()
        {
            return (os.contains("win"));
        }

         boolean isMac()
        {
            return (os.contains("mac"));
        }

        boolean isUnix()
        {
            return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
        }
    }

}
