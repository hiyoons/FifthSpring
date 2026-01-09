
//게시물 저장
const saveBtn = document.getElementById('save-button');


if(saveBtn) {

    saveBtn.addEventListener('click',

        (event) => {
            event.preventDefault();
            console.log("클릭!");
            const musicDataArray = Array.from(
              document.querySelectorAll("#selectedMusicContainer #playItem a")
            ).map(anchor => ({
              href: anchor.getAttribute("href"),
              text: anchor.textContent
            }));
const latitudeStr = document.querySelector('#latitude').innerHTML;
const longitudeStr = document.querySelector('#longitude').innerHTML;
            console.log(latitudeStr);
            console.log(longitudeStr);
            console.log(JSON.stringify(musicDataArray));
            console.log(document.querySelector('#lotNumberAddress').innerHTML);
            let addressStr=document.querySelector('#lotNumberAddress').innerHTML;
            let playLists=musicDataArray;
             body = JSON.stringify({
                        latitude: Number(latitudeStr),
                        longitude: Number(longitudeStr),
                        address: addressStr,
                        tagList: hashtags,
                        songList: playLists
                    });
                     function success() {
                        alert('등록 완료되었습니다.');
                        alert(body);

                        location.replace('/playlist/all');
                    };
                    function fail() {
                        alert('등록 실패했습니다.');
                        alert(body);
                        location.replace('/playlist/all');
                    };

                    httpRequest('POST',"/map/location/music/add", body, success, fail);


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

//플레이리스트 기능
const musicDataArray = document.getElementById("selectedMusicContainer");
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
          console.log("existHashList"+existHashList);
    }

    catch(error){
           console.error("JSON.parse 에러:", error.message);
           console.error("에러 발생 위치:", error.stack);
     }

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


function removeTag(button, tagName) {
    // 버튼의 부모 <p> 요소 찾아서 제거
    const tagElement = button.closest('p');
    if (tagElement) {
        tagElement.remove();
        tags.delete(tagName);
        updateHiddenInput();
    }
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

