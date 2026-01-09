//게시물 저장
const saveBtn = document.getElementById('save-button');

if(saveBtn) {

    saveBtn.addEventListener('click',

        (event) => {
            event.preventDefault();
            console.log("클릭!");
             body = JSON.stringify({
                        title: document.getElementById('title').value,
                        content: document.getElementById('content').value,
                        tagList: hashtags
                    });
                    function success() {
                        alert('등록 완료되었습니다.');
                        alert(body);
                        alert(hashtags);
                        location.replace('/article/list');
                    };
                    function fail() {
                        alert('등록 실패했습니다.');
                        alert(body);
                        alert(hashtags);
                        location.replace('/article/list');
                    };

                    httpRequest('POST','/article/add', body, success, fail);


        }
    )
}


function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}



// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}


//해시태그 기능
const hashtagsInput = document.getElementById("hashtags");
const hashtagsContainer = document.getElementById("hashtags-container");
const hiddenHashtagsInput = document.getElementById("hashtags-hidden");

const existTagInput=document.getElementById("existTag");
const existHashtagsEditInput = document.getElementById("tags-view");
const hiddenExistHashInput=document.getElementById("existTags-hidden");

const nowHashtags=document.getElementById("existHashtags");
let existHashList=[];
let beforeTag=[];


if(hiddenExistHashInput!==null&&hiddenExistHashInput.value!==null){

    let existHash=hiddenExistHashInput.value;
    console.log(typeof existHash);

    try {
           let existHashString = existHash.replace(/\[([^\]]+)\]/, function(match, content) {
                                         return '["' + content.split(',').map(s => s.trim()).join('","') + '"]';
         });

          existHashList = JSON.parse(existHashString);


    }

    catch(error){

     }

}

if(existHashtagsEditInput!=null){
    console.log("nowHashtags: "+hiddenExistHashInput.value);

    document.querySelectorAll('.tag-input').forEach(input=>{

        const index=parseInt(input.dataset.id);

        input.addEventListener('keydown',function(e){
            //enter키 이후
            if(e.key==='Enter') {
                const value=e.target.value;
                editHashtag(index,value);
            }
        })
    })

}

function removeBtn(button) {
    const tagDiv = button.closest('.hashtag');
    const target = button.previousElementSibling.value;

    if (tagDiv) {
        existHashList = existHashList.filter((hashtag) => hashtag !== target);
        tagDiv.remove();

        hiddenExistHashInput.value=hashtags.join(",");
    }
}

function editHashtag(index,value){
   if(existHashList[index]!==value){
        existHashList[index-1]=value;

   }
}

let hashtags=[];

function addHashtag(tag) {
       console.log(tag);
               tag = tag.replace(/[\[\]]/g, '').trim();
                if(tag && !hashtags.includes(tag)) {
                    const span = document.createElement("span");
                    span.innerText = "#" + tag + " ";
                    span.classList.add("hashtag");

                    const removeButton = document.createElement("button");
                    removeButton.innerText = "x";
                    removeButton.classList.add("remove-button");
                    removeButton.addEventListener("click", () => {
                        hashtagsContainer.removeChild(span);
                        hashtags = hashtags.filter((hashtag) => hashtag !== tag);
                        hiddenHashtagsInput.value = hashtags.join(",");
                    });

                    span.appendChild(removeButton);
                    hashtagsContainer.appendChild(span);
                    hashtags.push(tag);
                    hiddenHashtagsInput.value = hashtags.join(",");
                }
}

 if(hashtagsInput!=null){  hashtagsInput.addEventListener("keydown", (event) => {
                if (event.key === 'Enter') {
                    event.preventDefault();
                    const tag = hashtagsInput.value.trim();

                    if (tag) {
                        addHashtag(tag);
                        hashtagsInput.value = "";
                    }
                }
            });
        }