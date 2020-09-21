package com.ks.plugin;

/**
 * @author Wiser
 * 
 *         代码工具
 */
public class CodeTool {

    /**
     * KsActivity java生成代码
     *
     * @param dir 目录
     * @param className 类名
     * @return
     */
    public static String KsActivityJavaCode(String dir, String className, boolean isCreateViewModel, String viewModelClassName,boolean isCreateLayout,String layoutName) {
        return "";
    }

    /**
     * KsActivity kotlin生成代码
     *
     * @param dir 目录
     * @param className 类名
     * @return
     */
    public static String KsActivityKotlinCode(String dir, String className, boolean isCreateViewModel, String viewModelPackage,String viewModelClassName,boolean isCreateLayout,String layoutName) {

        return "package "+ dir + "\n" +
                "\n" +
                "import com.ks.picturebooks.base.ui.AbsActivity\n" +
                "import com.ks.frame.net.NetState\n" +
                ""+(isCreateLayout ? "import com.ks.picturebooks.R" +"\n": "") +
                ""+(isCreateViewModel ? "import "+viewModelPackage+"."+viewModelClassName+"" +"\n": "") +
                "import dagger.hilt.android.AndroidEntryPoint\n" +
                "\n" +
                "/**\n" +
                " ***************************************\n" +
                " * 项目名称:${PROJECT_NAME}\n" +
                " * @Author username\n" +
                " * 邮箱：username@ksjgs.com\n" +
                " * 创建时间: ${DATE}     ${TIME}\n" +
                " * 用途\n" +
                " ***************************************\n" +
                " */\n" +
                "@AndroidEntryPoint\n" +
				"class " + className + " : AbsActivity" + (isCreateViewModel ? "<" + viewModelClassName+">" : "<Nothing>")+"() {\n" +
                "\n" +
                "    override fun getLayoutResId() = "+(isCreateLayout ? "R.layout."+layoutName : 0)+"\n" +
                "\n" +
                "    override fun initData() {\n" +
                "    }\n" +
                "\n" +
                "    override fun initView() {\n" +
                "    }\n" +
                "\n" +
                "    override fun isUseEventBus(): Boolean {\n" +
                "        return false\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     *\n" +
                "     * 子类是否监听网络状态变化\n" +
                "     * return true 监听网络变化 ，false 不监听\n" +
                "     */\n" +
                "    override fun isMonitNetworkChange(): Boolean {\n" +
                "        return true\n" +
                "    }\n" +
                "\n" +
                "    override fun onNetChange(netState: NetState?) {\n" +
                "        super.onNetChange(netState)\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 数据金鼎绑定\n" +
                "     */\n" +
                "    override fun startObserve() {\n" +
                "\n" +
                "    }\n" +
                "}";
    }

    /**
     * KsFragment java生成代码
     *
     * @param dir 目录
     * @param className 类名
     * @return
     */
    public static String KsFragmentJavaCode(String dir, String className, boolean isCreateViewModel, String viewModelClassName,boolean isCreateLayout,String layoutName){
        return "";
    }

    /**
     * KsFragment kotlin生成代码
     *
     * @param dir 目录
     * @param className 类名
     * @return
     */
    public static String KsFragmentKotlinCode(String dir, String className, boolean isCreateViewModel, String viewModelPackage,String viewModelClassName,boolean isCreateLayout,String layoutName){
        return "package "+dir+"\n" +
                "\n" +
                "import com.ks.picturebooks.base.ui.AbsFragment\n" +
                "import com.ks.frame.net.NetState\n" +
                ""+(isCreateLayout ? "import com.ks.picturebooks.R" +"\n": "") +
                ""+(isCreateViewModel ? "import "+viewModelPackage+"."+viewModelClassName+"" +"\n": "") +
                "\n" +
                "/**\n" +
                " ***************************************\n" +
                " * 项目名称:${PROJECT_NAME}\n" +
                " * @Author username\n" +
                " * 邮箱：username@ksjgs.com\n" +
                " * 创建时间: ${DATE}     ${TIME}\n" +
                " * 用途\n" +
                " ***************************************\n" +
                " */\n" +
                "class "+className+" : AbsFragment"+(isCreateViewModel ? "<"+viewModelClassName+">" : "<Nothing>")+"() {\n" +
                "\n" +
                "    override fun getLayoutResId() = "+(isCreateLayout ? "R.layout."+layoutName : 0)+"\n" +
                "\n" +
                "    override fun initData() {\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    override fun onNetChange(netState: NetState) {\n" +
                "        super.onNetChange(netState)\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 延迟初始化获取数据\n" +
                "     */\n" +
                "    override fun lazyInitData() {\n" +
                "    }\n" +
                "\n" +
                "    override fun initView() {\n" +
                "    }\n" +
                (isCreateViewModel ? "\n" +
                        "    /**\n" +
                        "     * 返回初始化 ViewModel对象\n" +
                        "     * @return VM\n" +
                        "     */\n" +
                        "    override fun initViewModel(): MainViewModel {\n" +
                        "        return getFragmentViewModel(MainViewModel::class.java)\n" +
                        "    }\n" +
                        "\n" : "") +
                "    override fun startObserve() {\n" +
                "\n" +
                "    }\n" +
                "}";
    }

    /**
     * @param dir
     * @param viewModelClassName
     * @param repositoryPackage
     * @param repositoryClassName
     * @return
     */
    public static String KsViewModelKotlinCode(String dir,String viewModelClassName,String repositoryPackage,String repositoryClassName) {
        return "package "+dir+"\n" +
                "\n" +
                "import androidx.hilt.lifecycle.ViewModelInject\n" +
                "import com.ks.frame.mvvm.BaseViewModel\n" +
                "import "+repositoryPackage+"."+repositoryClassName+"\n" +
                "import javax.inject.Inject\n" +
                "\n" +
                "/**\n" +
                " ***************************************\n" +
                " * 项目名称:${PROJECT_NAME}\n" +
                " * @Author username\n" +
                " * 邮箱：username@ksjgs.com\n" +
                " * 创建时间: ${DATE}     ${TIME}\n" +
                " * 用途\n" +
                " ***************************************\n" +
                " */\n" +
                "class "+viewModelClassName+" @ViewModelInject constructor() : BaseViewModel() {\n" +
                "\n" +
                "    @Inject\n" +
                "    lateinit var repository: "+repositoryClassName+"\n" +
                "\n" +
                "    fun testData() {\n" +
                "        launchOnIO {\n" +
                "            TODO(\"Not yet implemented\")\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    public static String KsRepositoryKotlinCode(String repositoryPackage,String repositoryClassName,String serviceClassName) {
        return "package "+repositoryPackage+"\n" +
                "\n" +
                "import com.ks.component.network.model.repository.KsBaseRepository\n" +
                "import com.ks.frame.net.bean.KsResult\n" +
                "import javax.inject.Inject\n" +
                "\n" +
                "/**\n" +
                " ***************************************\n" +
                " * 项目名称:${PROJECT_NAME}\n" +
                " * @Author username\n" +
                " * 邮箱：username@ksjgs.com\n" +
                " * 创建时间: ${DATE}     ${TIME}\n" +
                " * 用途\n" +
                " ***************************************\n" +
                " */\n" +
                "class "+repositoryClassName+" @Inject constructor() : KsBaseRepository() {\n" +
                "    val name: String? = null\n" +
                "    suspend fun fetchData(): KsResult<String> {\n" +
                "        return safeApiCall(call = { doFetchData() })\n" +
                "    }\n" +
                "\n" +
                "    private suspend fun doFetchData(): KsResult<String> =\n" +
                "        executeResponse(getService("+serviceClassName+"::class.java).getUerInfo())\n" +
                "\n" +
                "}";
    }

    public static String KsServiceKotlinCode(String repositoryPackage,String serviceClassName) {
        return "package "+repositoryPackage+"\n" +
                "\n" +
                "import com.ks.frame.net.bean.KsResponse\n" +
                "import retrofit2.http.GET\n" +
                "\n" +
                "/**\n" +
                " ***************************************\n" +
                " * 项目名称:${PROJECT_NAME}\n" +
                " * @Author username\n" +
                " * 邮箱：username@ksjgs.com\n" +
                " * 创建时间: ${DATE}     ${TIME}\n" +
                " * 用途\n" +
                " ***************************************\n" +
                " */\n" +
                "interface "+serviceClassName+" {\n" +
                "    @GET\n" +
                "    suspend fun getUerInfo(): KsResponse<String>\n" +
                "}";
    }

    /**
     * WISER View xml 代码
     * @return
     */
    public static String KsViewXmlCode(){
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:orientation=\"vertical\">\n" +
                "\n" +
                "</LinearLayout>";
    }

}
