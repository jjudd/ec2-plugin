/*
 * The MIT License
 *
 * Copyright (c) 2004-, Kohsuke Kawaguchi, Sun Microsystems, Inc., and a number of other of contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.plugins.ec2;

import hudson.Extension;
import hudson.util.FormValidation;
import hudson.util.IOException2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerResponse;

import com.amazonaws.auth.AWSCredentialsProvider;

/**
 * Eucalyptus.
 *
 * @author Kohsuke Kawaguchi
 */
public class Eucalyptus extends EC2Cloud {
    public final URL ec2endpoint;
    public final URL s3endpoint;

    @DataBoundConstructor
    public Eucalyptus(URL ec2endpoint, URL s3endpoint, boolean useInstanceProfileForCredentials, boolean useSignerOverride, String accessId, String secretKey, String privateKey, String instanceCapStr, List<SlaveTemplate> templates) throws IOException {
        super("eucalyptus", useInstanceProfileForCredentials, useSignerOverride, accessId, secretKey, privateKey, instanceCapStr, templates);
        this.ec2endpoint = ec2endpoint;
        this.s3endpoint = s3endpoint;
    }

    @Override
    public URL getEc2EndpointUrl() throws IOException {
        return this.ec2endpoint;
    }

    @Override
    public URL getS3EndpointUrl() throws IOException {
        return this.s3endpoint;
    }

    @Extension
    public static class DescriptorImpl extends EC2Cloud.DescriptorImpl {
        @Override
		public String getDisplayName() {
            return "Eucalyptus";
        }

        @Override
		public FormValidation doTestConnection(
                @QueryParameter URL ec2endpoint,
                @QueryParameter boolean useInstanceProfileForCredentials,
                @QueryParameter boolean useSignerOverride,
                @QueryParameter String accessId,
                @QueryParameter String secretKey,
                @QueryParameter String privateKey) throws IOException, ServletException {
            return super.doTestConnection(ec2endpoint, useInstanceProfileForCredentials, useSignerOverride, accessId, secretKey, privateKey);
        }

		public FormValidation doGenerateKey(
                StaplerResponse rsp, @QueryParameter URL url, @QueryParameter boolean useInstanceProfileForCredentials, @QueryParameter boolean useSignerOverride, @QueryParameter String accessId, @QueryParameter String secretKey) throws IOException, ServletException {
            return super.doGenerateKey(rsp, url, useInstanceProfileForCredentials, useSignerOverride, accessId, secretKey);
        }
    }
}
