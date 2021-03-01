import React from 'react'
import {Upload,Button} from 'antd'
export default function IndexPage() {
  const [url,setUrl] = React.useState('')
  return (
    <div>
      <Upload.Dragger
       name="file"
      action="/api/picture/upload"
      accept=".jpg, .jpeg, .png, .gif"
      onChange={info=>{
        if(info.file.status === 'done'){
          setUrl("https://felis.top:9000/picture?md5="+info.file.response.info)
        }
      }}
      >
        <Button>上传图片</Button>
      </Upload.Dragger>
      <div style={{fontSize:'16px',textAlign:'center'}}>{url}</div>
    </div>
  );
}
