let musicDataArray = [];
const updateButton = document.getElementById('updateBtn');
if(updateButton) {

    updateButton.addEventListener('click',

        (event) => {
            event.preventDefault();
            console.log("클릭!");

            const selectedMusicList = document.querySelectorAll("#selected #playItem a");
            console.log(selectedMusicList);
            if (selectedMusicList.length > 0){
                 musicDataArray = Array.from(
                              selectedMusicList
                            ).map(anchor => ({
                              href: anchor.getAttribute("href"),
                           text: anchor.querySelector("button.btn-success").textContent.trim()

//                              text: anchor.textContent
                            }));

            }
            else{
                console.log("musicData Array없음");

            }

        const latitudeStr = document.querySelector('#latitude').innerHTML;
        const longitudeStr = document.querySelector('#longitude').innerHTML;
            console.log(latitudeStr);
            console.log(longitudeStr);
            console.log(JSON.stringify(musicDataArray));

        let addressStr = document.getElementById('address').textContent;
            console.log(addressStr);
         let articleId = document.getElementById('article-id').value;
         console.log('article-id');
         console.log(articleId);
            if(document.querySelector('#lotNumberAddress'))
            {console.log(document.querySelector('#lotNumberAddress').innerHTML);
             let addressStr=document.querySelector('#lotNumberAddress').innerHTML;}
            else{
                let addressStr = document.getElementById('address').textContent;
                console.log(document.getElementById('address'));
            }
            let playLists=musicDataArray;
             body = JSON.stringify({
                        id : Number(articleId),
                        latitude: Number(latitudeStr),
                        longitude: Number(longitudeStr),
                        address: addressStr,
                        tagList: hashtags,
                        songList: playLists
                    });
                     function success() {
                        alert('업데이트 완료되었습니다.');
                        alert(body);

                        location.replace('/playlist/all');
                    };
                    function fail() {
                        alert('업데이트 실패했습니다.');
                        alert(body);
                        location.replace('/playlist/all');
                    };

                    httpRequest('PUT','/playlist/edit', body, success, fail);


        }
    )
}


const deleteBtn = document.getElementById('deleteBtn');
if(deleteBtn){
    deleteBtn.addEventListener('click', (event)=>{
    const playlistID = document.getElementById('playlist-id').innerText;
                            function success() {
                            alert('삭제 완료되었습니다.');

                            location.replace('/playlist/all');
                        };
                        function fail() {
                            alert('삭제 실패했습니다.');
                            location.replace('/playlist/all');
                        };
                httpRequest('DELETE','/playlist/delete?id='+Number(playlistID), null, success, fail);


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



//플레이리스트 기능
console.log("musicDataArray");
console.log(musicDataArray);


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
//          beforeTagList=existHashList;
          console.log("existHashList"+existHashList);
    }

    catch(error){
           console.error("JSON.parse 에러:", error.message);
           console.error("에러 발생 위치:", error.stack);
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
                console.log(`${index} 번째 Enter눌림, 값:`,value);
                editHashtag(index,value);
            }
        })
    })

}

let hashtags=[];
if(hashtagsInput!=null){  hashtagsInput.addEventListener("keydown", (event) => {
                if (event.key === 'Enter') {

                    const tag = hashtagsInput.value.trim();
                    console.log("enter 클릭!");
                    console.log(tag);
                    if (tag) {
                        addHashtag(tag);
//                        hashtagsInput.value = "";
                       document.getElementById('hashtags').value = "";
                    }
                }
            });
        }
function addHashtag(tag) {
       console.log(tag);
               tag = tag.replace(/[\[\]]/g, '').trim();
                if(tag && !hashtags.includes(tag)) {
                    const p = document.createElement("p");
                    p.innerHTML = `
                       <a type="button">
                           <button class="btn btn-dark">${tag}</button>
                       </a>
                        <a type="button">
                                <button id="remove-btn"  type="button" class="btn btn-default" onclick="removeBtn(this)"
                                                           data-tag="${tag}">
                                    <i class="bi bi-x-square-fill"></i>
                                </button>

                       </a>
                    `;



                    p.classList.add("hashtag");

                    hashtagsContainer.appendChild(p);
                    hashtags.push(tag);
                    hiddenHashtagsInput.value = hashtags.join(",");

                }

}



function removeExistTag(tag){
    tag.preventDefault();
    const clickTag = button.getAttribute('data-tag');
    console.log("삭제 태그: ",tag);

    existHashList = existHashList.filter((tag)=> tag!=clickTag);
    console.log("업데이트 된 existHashtag",existHashList);



}
function removeBtn(button) {
    const tag = button.getAttribute('data-tag');
    const p = button.closest('p');

    if (p) {
        hashtagsContainer.removeChild(p);
        hashtags = hashtags.filter((hashtag) => hashtag !== tag);
        hiddenHashtagsInput.value = hashtags.join(",");
    }
}
function updateHiddenInput() {
    const hiddenInput = document.getElementById('hashtags-hidden');
    hiddenInput.value = Array.from(tags).join(',');
}


