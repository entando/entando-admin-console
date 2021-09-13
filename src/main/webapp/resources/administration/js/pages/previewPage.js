const sizeOptions = {
	desktop: {
		width: '100%',
		height: '100%'
	},
	tablet: {
		width: '768px',
		height: '1024px'
	},
	smartphone: {
		width: '360px',
		height: '640px'
	}
};

var previewFrame, mainContainer, controlBar, commentBar, commentsOpened = false;

const toggleCommentsBar = (open = false) => {
	commentsOpened = open;
	if (commentsOpened) {
		mainContainer.classList.add('show-comments');
	} else {
		mainContainer.classList.remove('show-comments');
	}
};

const handleChangePreviewSize = (ev) => {
	const { width, height } = ev && ev.detail || {
		width: PROPERTY.previewWidth,
		height: PROPERTY.previewHeight,
	};
	previewFrame.setAttribute('width', width.replace(/px/i, ''));
	previewFrame.setAttribute('height', height.replace(/px/i, ''));
};

const handleCloseComments = () => {
	toggleCommentsBar(false);
	controlBar.setAttribute('comments-opened', 'false');
};

const handleChangeIframeUrl = (ev) => {
	const lang = ev && ev.detail || PROPERTY.lang;
	var normalizedBaseUrl = PROPERTY.baseUrl.replace(/\/$/, ''),
		previewUrl = [normalizedBaseUrl, 'preview', lang, PROPERTY.pageCode].join('/');
	previewUrl += '?' + [ 'token='+encodeURIComponent(PROPERTY.token) ].join('&');

	previewFrame.setAttribute('src', previewUrl);
}

const pageReady = () => {
	previewFrame = document.getElementById('previewFrame');
	mainContainer = document.querySelector('.main-container');
	controlBar = document.getElementById('controlBar');
	commentBar = document.querySelector('preview-comments-bar');
	controlBar.addEventListener(PreviewControlBarEvent.RESOLUTION_CHANGE, handleChangePreviewSize);
	controlBar.addEventListener(PreviewControlBarEvent.CHANGE_LANGUAGE, handleChangeIframeUrl);
	controlBar.addEventListener(PreviewControlBarEvent.COMMENT_TOGGLE, ({ detail }) => toggleCommentsBar(detail));

	commentBar.addEventListener(PreviewCommentsBarEvent.COMMENT_CLOSE, handleCloseComments);
	handleChangeIframeUrl();
	handleChangePreviewSize();
}

document.addEventListener("DOMContentLoaded", pageReady);
