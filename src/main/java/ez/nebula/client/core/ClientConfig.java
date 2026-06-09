package ez.nebula.client.core;

import ez.nebula.client.BuildConfig;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class ClientConfig
{
    public static final String SHORT_VERSION = String.format("%s+%s",
            BuildConfig.VERSION, BuildConfig.BUILD);
    public static final String VERSION = String.format("%s/%s-%s",
            SHORT_VERSION, BuildConfig.BRANCH, BuildConfig.HASH);

    public static final String GITHUB_REPO = "https://github.com/xgraza/nebula-b1.7.2/tree/"
            + BuildConfig.BRANCH;

    /**
     * If features should use heavier debugging
     */
    public static boolean DEBUG;
}