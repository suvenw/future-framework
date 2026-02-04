## 数据库表模型对象类
package ${packageName}.${moduleName}.entity;



import java.math.*;
import java.util.Date;
#if( $isLombok==1)
import lombok.Data;
import lombok.EqualsAndHashCode;
#end
/**
  * @ClassName: ${className}.java
  *
  * @Author 作者 : ${author}
  * @email 邮箱 : ${email}
  * @CreateDate 创建时间: ${datetime}
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  Description: ${description} 数据库表对应的实现类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * </pre>
  * Copyright: (c) 2021 gc by <a href="https://www.suven.top">suven</a>
  **/
#if( $isLombok==1)
@Data
@EqualsAndHashCode(callSuper=false)
#end
public class ${className} extends BaseTenantEntity{

private static final long serialVersionUID = 1L;

##  public interface DataSourceModuleName{
##          public static final String module_name_${moduleName} = "${moduleName}";
##  }

#foreach ($column in $columns)
#if( $column.fieldAttrName )
$column.docAttrName
$column.fieldAttrName
#end

#end

    public static ${className} build(){
        return new ${className}();
    }

#foreach ($column in $columns)
#if( $column.fieldAttrName )
$column.buildAttrName
#if( $isLombok==0)
    $column.setAttrName
    $column.getAttrName
#end
#end
#end
}